using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Extensions.Configuration;

namespace ProjectPRN
{
    internal class AppConfig
    {
        public static IConfigurationRoot Configuration { get; }
        static AppConfig()
        {
            var builder = new ConfigurationBuilder()
                .SetBasePath(Directory.GetCurrentDirectory())
                .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true);
            Configuration = builder.Build();
        }
        public static string GetAdminEmail()
        {
            return Configuration["AdminAccount:Email"];
        }

        public static string GetAdminPassword()
        {
            return Configuration["AdminAccount:Password"];
        }
    }
}
