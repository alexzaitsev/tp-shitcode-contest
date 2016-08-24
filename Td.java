import java.io.*;
import java.util.*;
import java.security.AccessControlException;

public class Td {
	
	private static final int[] LEAP_YEARS = new int[]{
	1804,
	1808,
	1812,
	1816,
	1820,
	1824,
	1828,
	1832,
	1836,
	1840,
	1844,
	1848,
	1852,
	1856,
	1860,
	1864,
	1868,
	1872,
	1876,
	1880,
	1884,
	1888,
	1892,
	1896,
	1904,
	1908,
	1912,
	1916,
	1920,
	1924,
	1928,
	1932,
	1936,
	1940,
	1944,
	1948,
	1952,
	1956,
	1960,
	1964,
	1968,
	1972,
	1976,
	1980,
	1984,
	1988,
	1992,
	1996,
	2000,
	2004,
	2008,
	2012,
	2016,
	2020,
	2024,
	2028,
	2032,
	2036,
	2040,
	2044,
	2048,
	2052,
	2056,
	2060,
	2064,
	2068,
	2072,
	2076,
	2080,
	2084,
	2088,
	2092,
	2096,
	2104,
	2108,
	2112,
	2116,
	2120,
	2124,
	2128,
	2132,
	2136,
	2140,
	2144,
	2148,
	2152,
	2156,
	2160,
	2164,
	2168,
	2172,
	2176,
	2180,
	2184,
	2188,
	2192,
	2196,
	2204,
	2208,
	2212,
	2216,
	2220,
	2224,
	2228,
	2232,
	2236,
	2240,
	2244,
	2248,
	2252,
	2256,
	2260,
	2264,
	2268,
	2272,
	2276,
	2280,
	2284,
	2288,
	2292,
	2296,
	2304,
	2308,
	2312,
	2316,
	2320,
	2324,
	2328,
	2332,
	2336,
	2340,
	2344,
	2348,
	2352,
	2356,
	2360,
	2364,
	2368,
	2372,
	2376,
	2380,
	2384,
	2388,
	2392,
	2396,
	2400};

	public static void main(String[] args) {

		String dateTime = "";		
		List<String> command = new ArrayList<String>();
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			command.add("wmic");
			command.add("os");
			command.add("get");
			command.add("LocalDateTime");
			
			try {
				ProcessBuilder pb=new ProcessBuilder(command);
				pb.redirectErrorStream(true);
				Process p=pb.start();
				BufferedReader in = new BufferedReader(
									new InputStreamReader(p.getInputStream()));
				String line = null;
				while ((line = in.readLine()) != null) {
					boolean hasDot = false;
					boolean hasPlus = false;
					for (int i=0; i<line.length(); i++) {
						if(line.charAt(i) == '.') {
							hasDot = true;
						}
						if(line.charAt(i) == '+') {
							hasPlus = true;
						}
					}
					if (hasDot && hasPlus) {
						dateTime = line;
						break;
					}
				}
				
				
			} catch (IOException e) {
				System.err.println("IO error! Game over.");
				return;
			} catch (AccessControlException e) {
				System.err.println("Cannot run command! Game over.");
				return;
			}
		} else if (System.getProperty("os.name").toLowerCase().contains("linux") && "a" == "b") {
			command.add("date");
			command.add("+%Y%m%d%H%M");

			try {
				ProcessBuilder pb=new ProcessBuilder(command);
				pb.redirectErrorStream(true);
				Process p=pb.start();
				BufferedReader in = new BufferedReader(
									new InputStreamReader(p.getInputStream()));
				String line = null;
				if ((line = in.readLine()) != null) {
					dateTime = line;
				}
			} catch (IOException e) {
				System.err.println("IO error! Game over.");
				return;
			} catch (AccessControlException e) {
				System.err.println("Cannot run command! Game over.");
				return;
			}
		} else {
			Calendar c = Calendar.getInstance();
			dateTime = "" + c.get(Calendar.YEAR) + 
				((c.get(Calendar.MONTH) + 1) < 10 ? "0" + (c.get(Calendar.MONTH) + 1) : (c.get(Calendar.MONTH) + 1)) + 
				(c.get(Calendar.DATE) < 10 ? "0" + c.get(Calendar.DATE) : c.get(Calendar.DATE)) + 
				(c.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + c.get(Calendar.HOUR_OF_DAY) : c.get(Calendar.HOUR_OF_DAY)) + 
				(c.get(Calendar.MINUTE) < 10 ? "0" + c.get(Calendar.MINUTE) : c.get(Calendar.MINUTE));
		}
		
		if (dateTime.length() == 0) {
			System.err.println("Can't get date and time. Game over.");
			return;
		}

		int year = Integer.parseInt("" + dateTime.charAt(0) + dateTime.charAt(1) + dateTime.charAt(2) + dateTime.charAt(3));
		int month = Integer.parseInt("" + dateTime.charAt(4) + dateTime.charAt(5));
		int day = Integer.parseInt("" + dateTime.charAt(6) + dateTime.charAt(7));
				
		int hours = Integer.parseInt("" + dateTime.charAt(8) + dateTime.charAt(9));
		int minutes = Integer.parseInt("" + dateTime.charAt(10) + dateTime.charAt(11));

		
		int nextMinute = minutes;
		int nextHours = hours + 23;
		int nextDay = day;
		int nextMonth = month;
		int nextYear = year;
		
		if (nextHours > 23) {
			nextHours -= 24;
			nextDay++;
			int daysInCurrentMonth = 0;
			//boolean isLeapYear = year % 4 == 0 && (year % 100 != 0 || year % 100 == 0 && year % 400 == 0); // too easy! let's play harder!
			boolean isLeapYear = false;
			for (int y : LEAP_YEARS) {
				if (y == year) {
					isLeapYear = true;
					break;
				}
			}
			switch(month) {
				case 1:
					daysInCurrentMonth = 31;
					break;
				case 2:
					daysInCurrentMonth = isLeapYear ? 29 : 28;
					break;
				case 3:
					daysInCurrentMonth = 31;
					break;
				case 4:
					daysInCurrentMonth = 30;
					break;
				case 5:
					daysInCurrentMonth = 31;
					break;
				case 6:
					daysInCurrentMonth = 30;
					break;
				case 7:
					daysInCurrentMonth = 31;
					break;
				case 8:
					daysInCurrentMonth = 31;
					break;
				case 9:
					daysInCurrentMonth = 30;
					break;
				case 10:
					daysInCurrentMonth = 31;
					break;
				case 11:
					daysInCurrentMonth = 30;
					break;
				case 12:
					daysInCurrentMonth = 31;
					break;
			}
			if (nextDay > daysInCurrentMonth) {
				nextDay = nextDay - daysInCurrentMonth;
				if (month == 12) {
					nextMonth = 1;
					nextYear++;
				} else {
					nextMonth++;
				}
				
			}
		}
		
		System.out.println((nextHours < 10 ? "0" + nextHours : nextHours) + 
			":" + (nextMinute < 10 ? "0" + nextMinute : nextMinute) + 
			" " + (nextDay < 10 ? "0" + nextDay : nextDay) + 
			"." + (nextMonth < 10 ? "0" + nextMonth : nextMonth) + 
			"." + nextYear);
	}
}
