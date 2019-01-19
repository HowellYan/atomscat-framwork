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
        paths.forEach((k,y)->{
            if(((JSONObject)y).containsKey("parameters") && ((JSONObject)y).get("parameters") instanceof JSONArray) {
                JSONArray parameters = (JSONArray) ((JSONObject)y).get("parameters");
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < parameters.size(); i++) {
//                    System.out.println(getBody((JSONObject) parameters.get(i), definitions));
                    jsonArray.add(i, getBody((JSONObject) parameters.get(i), definitions));
                }
                ((JSONObject)y).put("parameters", jsonArray);
            }
            if(((JSONObject)y).containsKey("responses")) {
                JSONObject responses = (JSONObject) ((JSONObject)y).get("responses");
                responses.forEach((k1,v1)->{
//                    System.out.println(getBody((JSONObject) v1, definitions));
                    responses.put(k1, getBody((JSONObject) v1, definitions));
                });
            }
        });
        return paths;
    }

    private JSONObject getNode(JSONObject paths, JSONObject definitions) {
        System.out.println(paths.toJSONString());
        paths.forEach((k ,v)->{
            if(k.toString().equals("$ref")) {
                String key = v.toString().replace("#/definitions/", "");
                JSONObject jsonObject = (JSONObject)definitions.get(key);
                if(jsonObject.toJSONString().contains("$ref")) {
                    paths.replace(k, getNode(jsonObject, definitions));
//                    System.out.println(getNode(jsonObject, definitions).toJSONString());
                } else {
                    paths.replace(k, jsonObject);
//                    System.out.println(jsonObject.toJSONString());
                }
            }
            if(v instanceof JSONObject){
                getNode((JSONObject)v, definitions);
            }
        });
        return paths;
    }

    private JSONObject getBody(JSONObject paths, JSONObject definitions){
        if (paths.containsKey("schema") &&  !((JSONObject)paths.get("schema")).containsKey("type") && ((JSONObject)paths.get("schema")).containsKey("$ref")) {
            String key = ((JSONObject)paths.get("schema")).get("$ref").toString().replace("#/definitions/", "");
            if(((JSONObject)definitions.get(key)).toJSONString().contains("$ref")){
               return getNode((JSONObject)definitions.get(key), definitions);
            }
            return (JSONObject)definitions.get(key);
        }
        return paths;
    }


}
