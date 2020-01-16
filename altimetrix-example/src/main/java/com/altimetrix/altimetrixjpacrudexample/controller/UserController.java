package com.altimetrix.altimetrixjpacrudexample.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.altimetrix.altimetrixjpacrudexample.exception.ResourceNotFoundException;
import com.altimetrix.altimetrixjpacrudexample.model.User;
import com.altimetrix.altimetrixjpacrudexample.service.UserService;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;


@Controller
@RequestMapping("/")
public class UserController 
{
    @Autowired
    UserService service;
 
    @RequestMapping
    public String getAllEmployees(Model model) 
    {
        List<User> list = service.getAllUsers();
 
        model.addAttribute("users", list);
        return "list-users";
    }
 
    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public String editUserById(Model model, @PathVariable("id") Optional<Long> id) 
                            throws ResourceNotFoundException 
    {
    	if (id.isPresent()) {
            User user = service.getUserById(id.get());
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", new User());
            
        }
       
        return "add-edit-user";
    }
     
    @RequestMapping(path = "/delete/{id}")
    public String deleteEmployeeById(Model model, @PathVariable("id") Long id) 
                            throws ResourceNotFoundException 
    {
        service.deleteUserById(id);
        return "redirect:/";
    }
 
    @RequestMapping(path = "/createUser", method = RequestMethod.POST)
    public String createOrUpdateUser(User user) 
    {
    	System.out.println("user create = "+user);
    	service.createOrUpdateUser(user);
        return "redirect:/";
    	   
    }
    
    // APi for reading the USER.xml from resources folder & adding data in to DB
    @RequestMapping(path = "/parseXMLtoDB")
    public String parseXML() 
    {
  	  try 
  	  {
	    ClassLoader classLoader = new UserController().getClass().getClassLoader();
	    File fXmlFile = new File(classLoader.getResource("User.xml").getFile());
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("user");
		
		for (int temp = 0; temp < nList.getLength(); temp++)
		{
			Node nNode = nList.item(temp);
			System.out.println("\nCurrent Element :" + nNode.getNodeName());
			if (nNode.getNodeType() == Node.ELEMENT_NODE) 
			{
				Element eElement = (Element) nNode;
				User user1 = new User();
				user1.setEmailId(eElement.getElementsByTagName("emailId").item(0).getTextContent());
				user1.setId(Long.parseLong(eElement.getAttribute("id")));
				user1.setFirstName(eElement.getElementsByTagName("firstname").item(0).getTextContent());
				user1.setLastName(eElement.getElementsByTagName("lastname").item(0).getTextContent());
				user1.setStatus(eElement.getElementsByTagName("status").item(0).getTextContent());
				user1.setPhoneNumber(eElement.getElementsByTagName("phoneNo").item(0).getTextContent());
				service.createOrUpdateUser(user1);
			}
		}
			
	 } catch (Exception e) 
  	 {
			e.printStackTrace();
	 }
  	
     return "redirect:/";
    }
    
   }