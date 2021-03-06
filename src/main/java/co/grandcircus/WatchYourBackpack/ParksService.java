package co.grandcircus.WatchYourBackpack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import co.grandcircus.WatchYourBackpack.Daos.BeastEventDao;
import co.grandcircus.WatchYourBackpack.Daos.ParksDao;
import co.grandcircus.WatchYourBackpack.Daos.WeatherEventDao;
import co.grandcircus.WatchYourBackpack.Entities.BeastEvent;
import co.grandcircus.WatchYourBackpack.Entities.DBPark;
import co.grandcircus.WatchYourBackpack.Entities.WeatherEvent;
import co.grandcircus.WatchYourBackpack.Models.CampgroundModel.Campground;
import co.grandcircus.WatchYourBackpack.Models.CampgroundModel.CampgroundResponse;
import co.grandcircus.WatchYourBackpack.Models.NPSModel.Park;

@Component
public class ParksService {

	@Autowired
	private ParksDao pDao;
	@Autowired
	private NPSApiService NPSapiServ;

	public String determineParkCode(String parkCodeName, String parkCodeState, String parkCodeFee) {
		
		List<String> codes = Arrays.asList(parkCodeName, parkCodeState, parkCodeFee);
		int emptyCount = (int) codes.stream().filter(str -> str.isEmpty()).count();
		switch (emptyCount) {
			case 2:			
				return codes.stream().filter(code -> !code.isEmpty()).collect(Collectors.toList()).get(0);
			case 3:
				return "none";
			default:
				return "many";		
		}		
	}
	
	
	///////////// Single-use Methods////////////////////////////////////////////////////////////////
	public void fillDatabase() {
		// Creating a set of park codes from the campground endpoint
		// List<String> parkCodes = NPSapiServ.getParkCodesWithCampgrounds();

		// Load database of park names and entrance fees
		List<Park> parks = NPSapiServ.getParks();
		//System.out.println(parks);

		Set<String> parkCodes = new HashSet<String>(Arrays.asList("fiis", "hale", "seki", "crla", "ciro", "chic",
				"tapr", "grpo", "band", "cebr", "neri", "fomr", "gumo", "dewa", "orpi", "dino", "badl", "cwdw", "blri",
				"ever", "cuis", "glca", "redw", "klgo", "care", "laro", "havo", "natr", "samo", "pore", "indu", "grsa",
				"labe", "wrst", "pinn", "prwi", "chir", "chis", "mora", "caco", "ozar", "slbe", "moja", "nabr", "cato",
				"zion", "arch", "katm", "buff", "crmo", "brca", "obed", "olym", "wica", "thro", "cany", "sagu", "rabr",
				"shen", "whis", "deto", "buis", "hosp", "gari", "elmo", "drto", "guis", "romo", "grte", "kefj", "nava",
				"jotr", "cuva", "yell", "apis", "grsm", "orca", "grca", "lake", "cana", "maca", "deva", "piro", "colm",
				"hove", "bica", "biso", "choh", "yose", "bicy", "glba", "bith", "isro", "dena", "lavo", "cuga", "bibe",
				"whsa", "lamr", "amme", "noca", "gett", "pais", "asis", "goga", "glac", "caha", "bisc", "gree", "gate",
				"niob", "ebla"));

		for (Park park : parks) {
			//System.out.println(park);
			if (parkCodes.contains(park.getParkCode())) {
				
			DBPark dbPark = new DBPark();
			
			//.isEmpty works for the following because entranceFees is a List
			Double entranceFee = (!park.getEntranceFees().isEmpty()
					? Double.parseDouble(park.getEntranceFees().get(0).getCost())
					: null);			
			dbPark.setEntranceFee(entranceFee);
			
			//.isEmpty does not work for the following because parkCode is a string. (it's not a null check)
			//we're just going to assume it's not null
			dbPark.setParkCode(park.getParkCode());
			
			//this one needs a null check because parks in DC don't have a state code XD		
			String stateCode = (park.getStates() != null ? park.getStates().substring(0, 2) : null);
			dbPark.setStateCode(stateCode);
			
			dbPark.setLongitude(park.getLongitude());			
			dbPark.setLatitude(park.getLatitude());			
			dbPark.setUrl(park.getUrl());			
			dbPark.setImageUrl(park.getImages().get(0).getUrl());			
			dbPark.setName(park.getName());		
			pDao.save(dbPark);
			}
		}
	}
	
	public void setRvOptionForParksInDatabase(List<DBPark> parks) {

		for (DBPark park : parks) {
			
			for (Campground campground : NPSapiServ.getCampgroundsByPark(park)) {
				if (park.getRvOption() == true) {
					break;
				}
				// if number of rvonly sites + number of electrical hookup sites > 0, rvOption = true
				Boolean rvOption = (Integer.parseInt(campground.getCampsites().getRvonly())
						+ Integer.parseInt(campground.getCampsites().getElectricalhookups()) > 0);
				park.setRvOption(rvOption);				
				pDao.save(park);
			}
		}
	}
	public void setReceptionForParksInDatabase(List<DBPark> parks) {

		for (DBPark park : parks) {
			for (Campground campground : NPSapiServ.getCampgroundsByPark(park)) {
				if (park.getReception() == true) {
					break;
				}
				Boolean reception = (campground.getAmenities().getCellphonereception() == "Yes") ? true : false;
				park.setReception(reception);				
				pDao.save(park);
			}
		}
	}
}
