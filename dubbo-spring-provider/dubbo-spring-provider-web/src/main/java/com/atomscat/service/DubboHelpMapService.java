package com.atomscat.service;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class DubboHelpMapService {

    public JSONObject getHelpByKey(JSONObject jsonObject, String key){
        key = "/" + key;
        JSONObject paths = (JSONObject)((JSONObject)jsonObject.get("paths")).get(key);
        JSONObject definitions = (JSONObject)jsonObject.get("definitions");

        paths = merge(paths, definitions);
        return paths;
    }

    private JSONObject merge(JSONObject paths, JSONObject definitions) {
        if(paths.containsKey("parameters")&& paths.get("parameters") instanceof JSONArray){
            JSONArray parameters = (JSONArray)paths.get("parameters");
//            for (int i = 0; i < parameters.size(); i++) {
//                parameters.add(i, getBody((JSONObject) parameters.get(i), definitions));
//                merge((JSONObject) parameters.get(i), definitions);
//            }
            parameters.forEach((item)->{
                merge((JSONObject) item, definitions);
            });
        } else {
            paths.forEach((k, v)->{
                if(v instanceof JSONObject) {
                    merge((JSONObject) v, definitions);
                } else if (k.equals("$ref")) {
                    String key = v.toString().replace("#/definitions/", "");
                    paths.put(k, definitions.get(key));
                }
            });
        }
        return paths;
    }

    private JSONObject getBody(JSONObject paths, JSONObject definitions){
        if (paths.containsKey("schema") &&  !((JSONObject)paths.get("schema")).containsKey("type") && ((JSONObject)paths.get("schema")).containsKey("$ref")) {
            String key = ((JSONObject)paths.get("schema")).get("$ref").toString().replace("#/definitions/", "");
            return (JSONObject)definitions.get(key);
        } else {

        }
        return paths;
    }


}
