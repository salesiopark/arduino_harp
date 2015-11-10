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
using System.Windows.Threading;

namespace WpfTerm1
{
    /// <summary>
    /// MainWindow.xaml에 대한 상호 작용 논리
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        SerialPort serial;// = new SerialPort();
        string strComPort = null;
        int iBaudRate = 9600;
        bool bIsStarted = false;
        
        string g_sRecvData = String.Empty;
        delegate void SetTextCallBack(String text);

        private void Window_Initialized(object sender, EventArgs e)
        {
            string[] args = Environment.GetCommandLineArgs();
            this.Title = "Text Terminal ver 0.6";// +WpfTerm1.Args.args.ToString();
            
            // init BaudRate combobox
            comboBoxSpd.Items.Add("4800");
            comboBoxSpd.Items.Add("9600");
            comboBoxSpd.Items.Add("19200");
            comboBoxSpd.Items.Add("57600");
            comboBoxSpd.Items.Add("115200");

            if (args.Length != 3)
            {
                Just_return_on_error("Error : Invalid input parameters.");
                return;
            }

            if (args[1] != null)
            {
                this.Title += (" {" + args[1] + "}");
                strComPort = args[1];
            }
            else
            {
                this.Title = "Error: No COM port available";
                return;
            }

            

            if (args[2] != null)
            {
                this.Title += (" {" + args[2] + "}");
                switch (args[2]) {
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
                        this.Title = "Error : Baud rate not supported.";
                        btnSend.IsEnabled = false;
                        return;
                        break;
                }
            }
            else
            {
                Just_return_on_error("Error: No Baud rate available");
                return;
            }

            textBoxRead.FontSize = 20;
            serial = new SerialPort(strComPort, iBaudRate, Parity.None, 8, StopBits.One);
            serial.DataReceived += OnDataReceived;
            try {
                serial.Open();
                serial.DtrEnable = true; // reset arudino
                System.Threading.Thread.Sleep(100); // wait a little
                serial.ReadExisting();// flush existing garbage data.
                bIsStarted = true;
                //textBoxRead.Clear();
            }
            catch (Exception ee)
            {

            }

        }

        void Just_return_on_error(String strMsg)
        {
            textBoxRead.Text = strMsg;
            btnSend.IsEnabled = false;
            textBoxSend.IsEnabled = false;
        }

        void OnDataReceived(object sender, SerialDataReceivedEventArgs e) {
            if (!bIsStarted) return;

            SerialPort sp = (SerialPort)sender;
            try {
                string strInData = sp.ReadExisting();
                if ((strInData != string.Empty)) // && (g_sRecvData.Contains('\n')))
                {
                    this.Dispatcher.Invoke(new Action(() => {
                        textBoxRead.AppendText(strInData);
                        textBoxRead.ScrollToEnd();

                    }), DispatcherPriority.ContextIdle);
                    //Console.Write(strInData);
                }
            }
            catch (TimeoutException) {

            }
             //*/
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            serial.WriteLine(textBoxSend.Text);
            this.Dispatcher.Invoke(new Action(() =>
            {
                textBoxSend.Text = "";
            }), DispatcherPriority.ContextIdle);

        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            Application.Current.Shutdown(); // to kill process
        }

        private void textBoxSend_KeyDown(object sender, KeyEventArgs e)
        {
            if(e.Key == Key.Enter)
            {
                Button_Click(null, null);
            }
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
