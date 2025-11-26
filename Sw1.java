import swisseph.*;

public class Sw1 {
  static final double AU2km=SweConst.AUNIT/1000;

  public static void main(String[] p) {
    SwissEph sw=new SwissEph();
	CFmt f=new CFmt();
    // SweDate sd=new SweDate(1957,4,13,18+57./60.);
    SweDate sd=new SweDate(2025,11,25,0);

    // The calculated values will be returned in
    // this array:
    double[] res=new double[6];
    StringBuffer sbErr=new StringBuffer();
    int flags = SweConst.SEFLG_SPEED |
                SweConst.SEFLG_TOPOCTR;
    // Calculate for some place in India:
    sw.swe_set_topo(-122.91647,49.20234,400);

	var planet = SweConst.SE_MOON;
	sw.swe_set_sid_mode(SE_SIDM_LAHIRI);

// -topo-122.91647,49.20234,400
    int rc=sw.swe_calc_ut(sd.getJulDay(),
                          planet,
                          flags,
                          res,
                          sbErr);
	/* 
	int rc = sw.calc(sd.getJulDay(), planet, flags, res);
	*/

    if (sbErr.length()>0) {
      System.out.println(sbErr.toString());
    }
    if (rc==SweConst.ERR) {
      System.exit(1);
    }
	var snam = sw.swe_get_planet_name(planet);
    System.out.println(
        sw.swe_get_planet_name(planet)+":"+
        "\n\tLongitude:          "+res[0]+
        "\n\tLatitude:           "+res[1]+
        "\n\tDistance:           "+res[2]+" AU"+
        "\n\t                   ("+(res[2]*AU2km)+" km)"+
        "\n\tLongitudinal speed: "+res[3]+" degs/day");
        System.out.print(f.fmt("%10s",snam)+"\t"+f.fmt("%11.7f",res[0])+"\t"+
                         f.fmt("%10.7f",res[1])+"\t"+f.fmt("%10.7f",res[2])+"\t"+
                         f.fmt("%10.7f",res[3])+"\n");
  }
}

