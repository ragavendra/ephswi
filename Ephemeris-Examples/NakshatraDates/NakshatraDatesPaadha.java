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
	final static double TZ_OFFSET_HOURS = -8;	// Pacific

/*
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
		"Purva Phalguni",
		"Uttar Phalguni",
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
		"Purva Bhadrapada",
		"Uttara Bhadrapada",
		"Revati",
	};
*/
	final static String[] nakshatraNames = new String[] {
		"ಅಶ್ವಿನಿ",
		"ಭರಣಿ",
		"ಕೃತ್ತಿಕಾ",
		"ರೋಹಿಣಿ ",
		"ಮ್ರಿಗಾಶೀರ್ಷ",
		"ಆರ್ದ್ರ",
		"ಪುನರ್ವಸು",
		"ಪುಷ್ಯ",
		"ಆಶ್ಲೇಷ",
		"ಮಾಘ",
		"ಪೂರ್ವ ಫಲ್ಗುಣಿ",
		"ಉತ್ತರ ಫಲ್ಗುಣಿ",
		"ಹಸ್ತ",
		"ಚಿತ್ರ",
		"ಸ್ವಾತಿ",
		"ವಿಶಾಖ",
		"ಅನುರಾಧ",
		"ಜ್ಯೇಷ್ಠ",
		"ಮೂಲ",
		"ಪೂರ್ವ ಆಷಾಢ",
		"ಉತ್ತರ ಆಷಾಢ",
		"ಶ್ರಾವಣ",
		"ಶ್ರವಿಷ್ಠ",
		"ಶತಭಿಷಾ",
		"ಪೂರ್ವ ಭದ್ರಪಧ",
		"ಉತ್ತರ ಭಾದ್ರಪದ",
		"ರೇವತಿ", 	};

	SwissEph sw = new SwissEph("./ephe");

	public static void main(String[] p) {
		NakshatraDates sj = new NakshatraDates();
		SweDate sd = new SweDate();

		sd.setDate(2025, 12, 1, -TZ_OFFSET_HOURS);

		System.out.println("Some nakshatras for Bangalore (77.5667E 12.9667N) starting with Ashvini in January 1986.");
		for(int naksh = 0; naksh < 27; naksh++) {	// Nakshatra number zero based
			double nakshStart = sj.getNextNakshatraStart(sd.getJulDay(), naksh, true);
			double nakshEnd = sj.getNextNakshatraEnd(sd.getJulDay(), naksh, true);
			System.out.println(String.format("topocentric %-20s: %s - %s",
					nakshatraNames[naksh],
					sj.toDateString(nakshStart, TZ_OFFSET_HOURS),
					sj.toDateString(nakshEnd, TZ_OFFSET_HOURS)));

			// Same with geocentric positions:
			nakshStart = sj.getNextNakshatraStart(sd.getJulDay(), naksh, false);
			nakshEnd = sj.getNextNakshatraEnd(sd.getJulDay(), naksh, false);
			System.out.println(String.format(" geocentric %-20s: %s - %s\n",
					nakshatraNames[naksh],
					sj.toDateString(nakshStart, TZ_OFFSET_HOURS),
					sj.toDateString(nakshEnd, TZ_OFFSET_HOURS)));
		}
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
