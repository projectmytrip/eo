package com.eni.ioc.assetintegrity.graphql;

import com.eni.ioc.assetintegrity.dao.AssetIntegrityDao;
import com.eni.ioc.assetintegrity.service.AssetIntegrityService;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class GraphQLDataFetchers {
     
    @Autowired
    private AssetIntegrityDao assetIntegrityDao;
    @Autowired
    private AssetIntegrityService assetIntegrityService;
 
    public DataFetcher getCorrosionKpi() {
        return dataFetchingEnvironment -> {
            String asset = dataFetchingEnvironment.getArgument("asset");
            return assetIntegrityDao.getCorrosionKpi(asset);
        };
    }
    
    public DataFetcher getOperatingWindowKpi() {
        return dataFetchingEnvironment -> {
            String asset = dataFetchingEnvironment.getArgument("asset");
            return assetIntegrityDao.getOperatingWindowKpi(asset);
        };
    }

    public DataFetcher getIntegrityOperatingWindowKpi() {
        return dataFetchingEnvironment -> {
            String asset = dataFetchingEnvironment.getArgument("asset");
            return assetIntegrityDao.getIntegrityOperatingWindowKpi(asset);
        };
    }
    
    public DataFetcher getBacklogOperationalKpi() {
        return dataFetchingEnvironment -> {
            String asset = dataFetchingEnvironment.getArgument("asset");
            return assetIntegrityService.getBacklogKpi(asset);
        };
    }
    
    public DataFetcher getCriticalSignalsOperationalKpi() {
        return dataFetchingEnvironment -> {
            String asset = dataFetchingEnvironment.getArgument("asset");
            return assetIntegrityService.getCriticalSignalsKpi(asset);
        };
    }

    public DataFetcher getCorrosionBacteriaTable() {
        return dataFetchingEnvironment -> {
            String asset = dataFetchingEnvironment.getArgument("asset");  
            String area = dataFetchingEnvironment.getArgument("area");            
            return assetIntegrityService.getCorrosionBacteria(asset, area, parentArea(area, asset));
        };
    }

    public DataFetcher getCorrosionCNDCount() {
        return dataFetchingEnvironment -> {
            String asset = dataFetchingEnvironment.getArgument("asset");
            String year = dataFetchingEnvironment.getArgument("year");
            String area = dataFetchingEnvironment.getArgument("area");

            return assetIntegrityService.getCorrosionCNDCount(asset, area, year, parentArea(area, asset));
        };
    }

    public DataFetcher getCorrosionPiggingTable() {
        return dataFetchingEnvironment -> {
            String asset = dataFetchingEnvironment.getArgument("asset");
            String year = dataFetchingEnvironment.getArgument("year");
            String area = dataFetchingEnvironment.getArgument("area");

            return assetIntegrityService.getCorrosionPigging(asset, area, parentArea(area, asset));
        };
    }

    public DataFetcher getCorrosionCouponTable() {
        return dataFetchingEnvironment -> {
            String asset = dataFetchingEnvironment.getArgument("asset");
            String area = dataFetchingEnvironment.getArgument("area");

            return assetIntegrityService.getCorrosionCoupon(asset, area, parentArea(area, asset));
        };
    }

    public DataFetcher getCorrosionProtectionTable() {
        return dataFetchingEnvironment -> {
            String asset = dataFetchingEnvironment.getArgument("asset");
            String area = dataFetchingEnvironment.getArgument("area");

            return assetIntegrityService.getCorrosionProtection(asset, area, parentArea(area, asset));
        };
    }

    public DataFetcher getEVPMS() {
        return dataFetchingEnvironment -> {
            String asset = dataFetchingEnvironment.getArgument("asset");
            String area = dataFetchingEnvironment.getArgument("area");
            return assetIntegrityService.getEVPMSData(asset, area, parentArea(area, asset));
        };
    }

    public DataFetcher getJacketedPipes() {
        return dataFetchingEnvironment -> {
            String asset = dataFetchingEnvironment.getArgument("asset");
            String area = dataFetchingEnvironment.getArgument("area");
            return assetIntegrityService.getJacketedPipes(asset, area, parentArea(area, asset));
        };
    }
    
	private String parentArea(String subArea, String asset){
		return assetIntegrityService.parentArea(subArea, asset);
	}
    
}
