using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

using System.IO.Ports;
using System.Runtime.InteropServices;//for Console
using System.Windows.Threading; // for Timer class
using System.Text.RegularExpressions;

using OxyPlot;
using OxyPlot.Axes;
using OxyPlot.Series;
//using System.Windows.Threading; 

using System.IO;

namespace WpfTerm1
{
    /// <summary>
    /// MainWindow.xaml에 대한 상호 작용 논리
    /// </summary>
    public partial class MainWindow : Window
    {
        SerialPort serial;// = new SerialPort();
        string g_sRecvData = String.Empty;
        delegate void SetTextCallBack(String text);
        LineSeries[] lnsa = null;
        LinearAxis lnrAxsX, lnrAxsY;
        int iBaudRate = 9600;
        private void Window_Initialized(object sender, EventArgs e)
        {
            string[] args = Environment.GetCommandLineArgs();
            this.Title = "Plot Terminal ver 0.6 ";// +WpfTerm1.Args.args.ToString();

            
            var pm = new PlotModel();
            //lnrAxsX = new LinearAxis(AxisPosition.Bottom, 0, 30, 10, 5);
            lnrAxsX = new LinearAxis(AxisPosition.Bottom, 0,30);
            lnrAxsX.MajorGridlineStyle = LineStyle.Dash;
            lnrAxsX.MinorGridlineStyle = LineStyle.Dot;
            lnrAxsX.Title = "시간:초(time:sec)";

            //lnrAxsY = new LinearAxis(AxisPosition.Left, 0, 1, 50, 10);
            lnrAxsY = new LinearAxis(AxisPosition.Left, "데이터(Data)");
            //lnrAxsY.StartPosition = 0;
            //lnrAxsY.EndPosition = 0.5;
            lnrAxsY.MajorGridlineStyle = LineStyle.Dash;
            lnrAxsY.MinorGridlineStyle = LineStyle.Dot;
            pm.Axes.Add(lnrAxsX);
            pm.Axes.Add(lnrAxsY);

            plotView1.Model = pm;
            plotView1.Model.PlotType = PlotType.XY;

            /*
            lns1 = new LineSeries { Title = "Data1", StrokeThickness = 1 };
            //lns1.Points.Add(new DataPoint(0, 0));
            //lns1.Points.Add(new DataPoint(70, 100));
            plotView1.Model.Series.Add(lns1);
            */

            //textBoxRead.AppendText("COM35 opened");
            //ConsoleHelper.AllocConsole();

            comboBoxSpd.Items.Add("4800");
            comboBoxSpd.Items.Add("9600");
            comboBoxSpd.Items.Add("19200");
            comboBoxSpd.Items.Add("57600");
            comboBoxSpd.Items.Add("115200");
            comboBoxSpd.SelectedIndex = 3;

            if (args.Length !=3)
            {
                Just_return_on_error("Error : Invalid input parameters");
                return;
            }

            this.Title += ("{" + args[1] + " @ " + args[2] +"}");
            if (args[2] != null)
            {
                //this.Title += (" {" + args[2] + "}");
                switch (args[2])
                {
                    case "4800":
                        comboBoxSpd.SelectedIndex = 0;
                        iBaudRate = 4800;
                        break;
                    case "9600":
                        comboBoxSpd.SelectedIndex = 1;
                        iBaudRate = 9600;
                        break;
                    case "19200":
                        comboBoxSpd.SelectedIndex = 2;
                        iBaudRate = 19200;
                        break;
                    case "57600":
                        comboBoxSpd.SelectedIndex = 3;
                        iBaudRate = 57600;
                        break;
                    case "115200":
                        comboBoxSpd.SelectedIndex = 4;
                        iBaudRate = 115200;
                        break;
                    default:
                        Just_return_on_error("Error: No Baud rate supported.");
                        return;
                        //break;
                }
            }
            
            serial = new SerialPort(args[1], iBaudRate, Parity.None, 8, StopBits.One);
            serial.DataReceived += OnDataReceived;
            try
            {
                serial.Open();
                serial.DtrEnable = true; // reset arduino !!!
                System.Threading.Thread.Sleep(500);
                serial.ReadExisting();
            }
            catch (Exception ee)
            {
                Just_return_on_error("Error: Cannot open serial port.");
                return;
            }

            // plot update timer
            DispatcherTimer dispatcherTimer = new DispatcherTimer();
            dispatcherTimer.Tick += new EventHandler(UpdatePlot);
            dispatcherTimer.Interval = new TimeSpan(0, 0, 0, 0, 500); // 500ms
            dispatcherTimer.Start();
        }

        void Just_return_on_error(String strMsg)
        {
            textBoxSend.Text = strMsg;
            btnSave.IsEnabled = false;
            textBoxSend.IsEnabled = false;
        }

        void UpdatePlot(object sender, EventArgs e)
        {
            //this.Title = "timer";
            plotView1.Model.InvalidatePlot(true);
        }

        int iNumData = 0;
        void OnDataReceived(object sender, SerialDataReceivedEventArgs e)
        {
            SerialPort sp = (SerialPort)sender;
            try
            {
                string strInData = sp.ReadLine();// sp.ReadExisting();
                if ((strInData != string.Empty)) // && (g_sRecvData.Contains('\n')))
                {
                    string strIn = Regex.Replace(strInData, @"\s+", "");
                    string[] stra = strIn.Split(',');
                    double tm = double.Parse(stra[0]) / 1000.0; // 1st data must be TIME.


                    this.Dispatcher.Invoke(new Action(() =>
                    {
                        if (lnsa == null) // if the first data is received initialize LineSeries
                        {
                            iNumData = stra.Length - 1;
                            lnsa = new LineSeries[iNumData];
                            for (int k = 0; k < iNumData; k++)
                            {
                                double dt = double.Parse(stra[k + 1]);
                                lnsa[k] = new LineSeries { Title = String.Format("Data{0}", k + 1), StrokeThickness = 1 };
                                lnsa[k].Points.Add(new DataPoint(tm, dt));
                                plotView1.Model.Series.Add(lnsa[k]);

                                if (dt > lnrAxsY.Maximum) lnrAxsY.Maximum = dt;
                                if (dt < lnrAxsY.Minimum) lnrAxsY.Minimum = dt;
                            }
                            return;
                        }
                        //textBoxSend.Text = String.Format("[{0}]:[{1}], [{2}]", strIn, stra[0], stra[1]);
                        textBoxSend.Text = String.Format("[{0}]:{1}s, ", strIn, tm);
                        //lns1.Points.Add(new DataPoint(tm, dt));

                        for (int k = 0; k < iNumData; k++)
                        {
                            double dt = double.Parse(stra[k + 1]);
                            lnsa[k].Points.Add(new DataPoint(tm, dt));

                            if (dt > lnrAxsY.Maximum) lnrAxsY.Maximum = dt;
                            if (dt < lnrAxsY.Minimum) lnrAxsY.Minimum = dt;
                        }

                        double xMax = lnrAxsX.Maximum;
                        if (tm > xMax) lnrAxsX.Maximum = xMax + 20;

                    }), DispatcherPriority.ContextIdle);
                }
            }
            //catch (TimeoutException) {
            catch (Exception ee)
            {
                this.Dispatcher.Invoke(new Action(() =>
                {
                    textBoxSend.Text = "Data error.";
                }), DispatcherPriority.ContextIdle);
            }
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            OxyPlot.Wpf.PngExporter pe = new OxyPlot.Wpf.PngExporter();
            var f = File.Create(@"D:\t1.png");
            pe.Export(plotView1.Model, f);
            
        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            Application.Current.Shutdown(); // to kill process
        }

    }

    /*
    public class ConsoleHelper
    {
        [DllImport("kernel32.dll")]
        public static extern Boolean AllocConsole();

        [DllImport("kernel32.dll")]
        public static extern Boolean FreeConsole();
    }
     */
}
