package com.eni.ioc.properties.util;

import com.eni.ioc.pi.rest.response.CorrosionCNDModel;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CorrosionConstants {

    public static final String CORROSION_CND_PLANT = "ENI_UGIT";

    public static final List<CorrosionCNDModel> CND_MODELS = new LinkedList<>();
    
    static {
        //Apparecchiature
        CND_MODELS.add(new CorrosionCNDModel("2", "1", "5", "EQUIPMENT",
                new String[]{"3", "WORKING_TEST"}, new String[]{"7", "DECENNAL_TEST"}));
        //Linee
        CND_MODELS.add(new CorrosionCNDModel("11", "1", "9", "LINE",
                new String[]{"3", "FUNCTIONAL_TEST"}, new String[]{"24", "INTEGRITY_TEST"}));
        //Valvole
        CND_MODELS.add(new CorrosionCNDModel("12", "1", "2", "VALVE",
                new String[]{"5", "CALIBRATION"}));
    }

    public static final Set<String> CND_FILTER_STRUCTURE = new HashSet(Arrays.asList(
            "COMPRESSORI V360",
            "IMPIANTO ZOLFO V580",
            "Linea 1",
            "Linea 2",
            "Linea 3",
            "Linea 4",
            "Linea 5",
            "Parti Comuni Area 1",
            "Parti Comuni Area 2",
            "Parti Comuni Area 3",
            "Parti Comuni Area 4",
            "Parti Comuni Area 5",
            "C.LE MONTE ALPI",
            "ALLI1-ALLI3",
            "ALLI2",
            "Area Pozzo CF5-",
            "CF1",
            "CF2",
            "CM2D",
            "IMP.1",
            "INNESTO1",
            "INNESTO2",
            "MA1-MA2D",
            "MA1N-ME3",
            "MA3",
            "MA4",
            "MA5-OR",
            "MA6-MA7-MA8",
            "MAE1",
            "MAW1-ME4",
            "ME1",
            "ME5-OR",
            "MENW1-ME2-ME9",
            "MEW1-ALLI4-ME10-SIMULTANEA",
            "SEZ.1",
            "SEZ.2",
            "SEZ.3",
            "SEZ.4",
            "SEZ.5",
            "SEZ.6",
            "Volturino 1"
    ));

}
