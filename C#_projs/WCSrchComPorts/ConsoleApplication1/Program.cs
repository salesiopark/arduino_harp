using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO.Ports;
using System.Management;
using System.IO;

namespace ConsoleApplication1
{
    class Program
    {
        static void Main(string[] args)
        {
            ManagementObjectCollection mReturn;
            ManagementObjectSearcher mSearch;
            
            // the following does NOT detect usb-serial converting device
            //mSearch = new ManagementObjectSearcher("Select * from Win32_SerialPort");
            
            // the follwong serarch all the pnp devices
            mSearch = new ManagementObjectSearcher("Select * from Win32_PnPEntity");
            mReturn = mSearch.Get();

            foreach (ManagementObject mObj in mReturn)
            {
                string str = mObj["Name"].ToString();
                if (str.Contains("(COM"))
                    Console.WriteLine(str);
            }
        }
    }
}
