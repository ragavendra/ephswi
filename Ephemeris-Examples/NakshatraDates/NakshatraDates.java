import swisseph.*;

/**
* This class calculates some nakshatra starting and ending dates
* for february 1986 in India (time zone offset 5.5 hours).
*
* It calculates topocentric (for Bangalore) and geocentric dates,
* even though geocentric seems to be normally used in India.
*/
public class NakshatraDates {

	// final static double TZ_OFFSET_HOURS = 5.5;	// IST
	final static double TZ_OFFSET_HOURS = -8;	// IST
	final static String[] nakshatraNames = new String[] {
		"Ashvini",
		"Bharani",
		"Krittika",
		"Rohini",
		"Mrigashirsha",
		"Ardra",
		"Punarvasu",
		"Pushya",
		"Ashlesha",
		"Magha",
		"P Phalguni",
		"U Phalguni",
		"Hasta",
		"Chitra",
		"Svati",
		"Vishakha",
		"Anuradha",
		"Jyeshtha",
		"Mula",
		"Purvashadha",
		"Uttarashadha",
		"Shravan",
		"Shravishtha",
		"Shatabhisha",
		"P Bhadrapada",
		"U Bhadrapada",
		"Revati",
	};

	final static String[] raashiNames = new String[] {
		"Mesha", "Vrushabha", "Mithuna", "Karkaataka", "Simha", "Kanya", "Thula", "Vrushabha", "Dhanusu", "Makara", "Kumbha", "Meena"
	};

/*
static String nakshatras[] = { "Ashwini", "Bharani", "Krithika13", "Rohini", "Mrigashira22", "Aradra", "Punarvasu31", "Pushya", "Ashlesha", "Magha", "Poorva Phalguni Pubha", "Uttara Phalguni13", "Hashta", "Chitta22", "Swathi", "Vishakha31", "Anuradha", "Jyeshta", "Moola", "Purvashaada", "Uttarashaada13", "Shravana", "Dhanishta22", "Shathabisha", "Poorvapaada31", "Uttarapaada", "Revathi" };

	final static String[] nakshatraNames = new String[] {
		"ಅಶ್ವಿನಿ",
		"ಭರಣಿ",
		"ಕೃತ್ತಿಕಾ13",
		"ರೋಹಿಣಿ ",
		"ಮ್ರಿಗಾಶೀರ್ಷ",
		"ಆರ್ದ್ರ",
		"ಪುನರ್ವಸು31",
		"ಪುಷ್ಯ",
		"ಆಶ್ಲೇಷ",
		"ಮಾಘ",
		"ಪೂರ್ವ ಫಲ್ಗುಣಿ",
		"ಉತ್ತರ ಫಲ್ಗುಣಿ 13",
		"ಹಸ್ತ",
		"ಚಿತ್ರ",
		"ಸ್ವಾತಿ",
		"ವಿಶಾಖ 31",
		"ಅನುರಾಧ",
		"ಜ್ಯೇಷ್ಠ",
		"ಮೂಲ",
		"ಪೂರ್ವ ಆಷಾಢ",
		"ಉತ್ತರ ಆಷಾಢ 13",
		"ಶ್ರಾವಣ",
		"ಶ್ರವಿಷ್ಠ 22",
		"ಶತಭಿಷಾ",
		"ಪೂರ್ವ ಭದ್ರಪಧ 31",
		"ಉತ್ತರ ಭಾದ್ರಪದ",
		"ರೇವತಿ", 	};

	final static String[] raashiNames = new String[] {
"ಮೇಷ", "ವೃಷಭ", "ಮಿಥುನ", "ಕರ್ಕಾಟಕ", "ಸಿಂಹ","ಕನ್ಯಾ ", "ತುಲಾ", "ವ್ರಿಶ್ಚಿಕಾ", "ದನಸು", "ಮಕರ", "ಕುಂಭ"," ಮೀನಾ"
	};
*/
		SwissEph sw = new SwissEph("./ephe");

	public static void main(String[] p) {
		NakshatraDates sj = new NakshatraDates();
		SweDate sd = new SweDate();

		sd.setDate(2025, 12, 1, -TZ_OFFSET_HOURS);

		System.out.println("Some nakshatras for Bangalore (77.5667E 12.9667N) starting with Ashvini in January 1986.");

		System.out.println("┌────────────────┬───────┬─────────────────┬───────────────────────────┐───────────────────────────┐");
		System.out.printf("│ %-14s │ %-5s │ %-15s | %-25s | %-25s │%n", "Nakshatra", "Paada", "Raashi", "Start", "End");
		System.out.println("├────────────────┼───────┼─────────────────┤───────────────────────────┤───────────────────────────├");
		// System.out.println("├────────────────┼───────┼───────┤");

		for(int naksh = 0; naksh < 27; naksh++) {	// Nakshatra number zero based
			double nakshStart = sj.getNextNakshatraStart(sd.getJulDay(), naksh, true);
			double nakshEnd = sj.getNextNakshatraEnd(sd.getJulDay(), naksh, true);
			int paada = naksh * 4;
			/* 
			System.out.printf("%-12s: %s %c; sign: %2d; %s in house %2d\n",
					 planetName, toDMS(xp[0]), (retrograde ? 'R' : 'D'), sign, toDMS(xp[0] % 30), house);
		return String.format("%3d°%02d'%07.4f\"", deg, min, sec);
			System.out.println(String.format("topocentric %-20s Paada# %-3d Raashi - %-10s: %s - %s",
			*/
			System.out.printf(String.format("│ %-14s │ %5d │ %15s │ %25s │ %25s │%n",
					nakshatraNames[naksh],
					paada,
					raashiNames[paada/9],
					sj.toDateString(nakshStart, TZ_OFFSET_HOURS),
					sj.toDateString(nakshEnd, TZ_OFFSET_HOURS)));
			/* 
			String st1 = "bigstring";
			String st2 = "midstring";
			String st3 = "bigstring";
			String st4 = "bigstring";
			st1 = nakshatraNames[naksh];
			// st2 = paada;
			st2 = raashiNames[paada/9];
			st3 = sj.toDateString(nakshStart, TZ_OFFSET_HOURS);
			st4 = sj.toDateString(nakshEnd, TZ_OFFSET_HOURS);

			System.out.printf("%-20s: %10s %40s - %30s\n", st1, st2, st3, st4);
			*/

			/* 
			// Same with geocentric positions:
			nakshStart = sj.getNextNakshatraStart(sd.getJulDay(), naksh, false);
			nakshEnd = sj.getNextNakshatraEnd(sd.getJulDay(), naksh, false);
			System.out.println(String.format(" geocentric %-20s: %s - %s\n",
					nakshatraNames[naksh],
					sj.toDateString(nakshStart, TZ_OFFSET_HOURS),
					sj.toDateString(nakshEnd, TZ_OFFSET_HOURS)));
			*/
		}
		System.out.println("└────────────────┴───────┴─────────────────┘───────────────────────────┘───────────────────────────┘");
	}

	double getNextNakshatraStart(double juld, int nakshatra, boolean topoctr) {
		double geopos[] = new double[] {77.5667, 12.9667, 0};

		sw.swe_set_sid_mode(SweConst.SE_SIDM_LAHIRI, 0, 0);
		sw.swe_set_topo(geopos[0], geopos[1], 0);
		int flags = SweConst.SEFLG_SWIEPH |
				SweConst.SEFLG_TRANSIT_LONGITUDE |
				SweConst.SEFLG_SIDEREAL;
		if (topoctr) {
			flags |= SweConst.SEFLG_TOPOCTR;
		}

		TransitCalculator tc = new TCPlanet(
				sw,
				SweConst.SE_MOON,
				flags,
				nakshatra * (360. / 27.));

		return sw.getTransitUT(tc, juld, false);
	}
	double getNextNakshatraEnd(double juld, int nakshatra, boolean topoctr) {
		return getNextNakshatraStart(juld, (nakshatra + 1) % 27, topoctr);
	}

	String toDateString(double d, double tzHours) {
		SweDate sd = new SweDate(d + tzHours / 24. + 0.5/24./3600. /* round to second */);
		double hour = sd.getHour();
		return String.format(java.util.Locale.US, "%4d/%02d/%02d, %2d:%02d:%02dh",
			sd.getYear(),
			sd.getMonth(),
			sd.getDay(),
			(int)hour,
			(int)((hour-(int)hour)*60),
			(int)((hour*60-(int)(hour*60))*60));
	}
}
