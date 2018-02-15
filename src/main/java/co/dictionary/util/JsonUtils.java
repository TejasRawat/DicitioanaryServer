package co.dictionary.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 
 * @author Pallabi Bhattacharya, 924052
 *
 */
public class JsonUtils {

	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Object convertJsonIntoClass(String jsonString,Class class_p){
		Object result = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.readValue(jsonString, class_p);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public String convertToJSON(Object inputObj) {
		ObjectMapper objectMapper = new ObjectMapper();
		String orderJson = null;
		try {
			orderJson = objectMapper.writeValueAsString(inputObj);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return orderJson;
	}
	
	
	
}
