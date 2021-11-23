package br.com.pessoa.controller.form;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReturnJsonNode {

	public String ReturnJsonNodeString(ResponseEntity<String> dados, String node){
	    ObjectMapper objectMapper = new ObjectMapper();
	    System.out.println(dados);
	    String nodereturn = "";
	        try {    
	            JsonNode jsonNodeRoot = objectMapper.readTree(dados.toString());
	            JsonNode jsNode = jsonNodeRoot.get(node);
	            nodereturn = jsNode.asText();
	            } catch (IOException ex) {
	            Logger.getLogger(ReturnJsonNode.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        return nodereturn;
	        
	    }
	
}
