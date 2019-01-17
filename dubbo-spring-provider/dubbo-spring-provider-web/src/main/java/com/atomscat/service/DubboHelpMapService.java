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

    public JSONObject merge(JSONObject paths, JSONObject definitions) {
        if(paths.containsKey("parameters")&& paths.get("parameters") instanceof JSONArray){
            JSONArray parameters = (JSONArray)paths.get("parameters");
//            for (int i=0; i< parameters.size();i++){
//                JSONObject item = (JSONObject) parameters.get(i);
//                String key = ((JSONObject)item.get("schema")).get("$ref").toString().replace("#/definitions/", "");
//                parameters.add(i, definitions.get(key));
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
                    paths.put("class", definitions.get(key));
                    //merge(paths, definitions);
                }
            });
        }
        return paths;
    }

}
