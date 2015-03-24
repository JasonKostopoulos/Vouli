/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xmlparser;


import com.mashape.unirest.http.JsonNode;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 *
 * @author Iasonas
 */
public class XmlMake {


        public XmlMake(){}
//  Function that creates the xml, over the given format
	public void makeXml(ArrayList<TNode> list, String name) {
                //Node node= new Node() {};
	  try {
              // Ints that are used as flags for ang y node in order to point existence
                int Ftheme=0;
                int Ftopic=0;
               
                
                // initializing the attributes of the xml
                Attr date = null;
                Attr session = null;
                Attr speaker = null;
                Attr theme = null;
                Attr topic = null;
                Attr speech =  null; 
                

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// root element with information about the given session and date
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("root");
                session=doc.createAttribute("Session");
                date= doc.createAttribute("Date");
              
                session.setValue(list.get(2).Session);
                rootElement.setAttributeNode(session);
                date.setValue(list.get(2).date);
                rootElement.setAttributeNode(date);
		doc.appendChild(rootElement);
                
                //main code for the xml creation
                
                NodeList rootchild;
                // loop over the list that is given as an attribute to the fuction.
                for(int i =0; i<list.size();i++){
//                    various tests over the list data
//                    System.out.println(list.get(i).speaker);
//                    System.out.println(list.get(i).speech);
                 
                 // initialize flags
                 Ftheme=0;
                 Ftopic=0;
                 
                   
                 //a list for the children of root node
                    rootchild = rootElement.getChildNodes();
                    
                    if(list.get(i)!=null){
                        if(list.get(i).theme!=null){
                            // if root node has children
                            if(rootElement.hasChildNodes()){

                                // loop over the children of root node.(themes)
                                for(int rc=0; rc<rootchild.getLength();rc++){
                                    
                                    // get each theme node on thnode
                                    Node   thnode   = rootchild.item(rc);
                                    Element thElement=(Element) thnode;
                                     
                                    //  if theme node is the same as the theme of the list element
                                    if(thElement.getAttribute("theme").equals(list.get(i).theme)){
                                        // test over the list themes information 
                                        //  System.out.println(list.get(i).theme);
                                        
                                        //initialize theme flag on true
                                        Ftheme=1;
                                        // check whether themeNOde has childNodes
                                        if(thnode.hasChildNodes()){
                                            // get a list of all child nodes
                                            NodeList thema = thnode.getChildNodes();
                                                // loope over all child nodes of theme (topic)
                                            for(int thc=0; thc<thema.getLength();thc++){
                                                // get each topic node
                                                Node tonode = thema.item(thc);
                                                Element toElement= (Element) tonode;
                                                // if  the topic node is the same to the list element topic
                                                if(toElement.getAttribute("topic").equals(list.get(i).topic)){
                                                  // test list topic information
                                                 //   System.out.println(list.get(i).topic);
                                                    
                                                    // initialize topic flag on true
                                                    Ftopic=1;
                                                    // if topic has childNodes
                                                    if(tonode.hasChildNodes()){
                                                        // get all child nodes on a list ( speakers)
                                                        NodeList titlos = tonode.getChildNodes();
                                                         // loop over each child (speaker)
//                                                        for(int toc=0; toc<titlos.getLength();toc++){
//                                                         // get each speaker 
//                                                          Node nnode = titlos.item(toc);
//                                                          Element nElement= (Element) nnode;
//                                                           // if speaker is same as list element's
//                                                          if(nElement.getAttribute("speaker").equals(list.get(i).name)){
//                                                                // various tests over speaker and speach elements of list
////                                                              System.out.println(list.get(i).speaker);
////                                                              System.out.println(list.get(i).speech);
//                                                              
//                                                              // flag speaker set on
//                                                              Fspeaker=1;
//                                                              
//                                                              //if speaker has child
//                                                              if(nnode.hasChildNodes()){
//                                                                  // get list with all child Nodes
//                                                                    NodeList omilies = nnode.getChildNodes();
//                                                                    // initialize speech element  and append on the node of the correct speaker
//                                                                    Element dspeech= doc.createElement("Speech"+ (omilies.getLength()+1));
//                                                                    dspeech.setAttribute("speech", list.get(i).speech);
//                                                                    nElement.appendChild(dspeech);
//                                                              }
//                                                              // if its the first speach
//                                                              else{
//                                                                  // append the element after initialization
//                                                                  Element dspeech= doc.createElement("Speech1");
//                                                                  dspeech.setAttribute("speech", list.get(i).speech);
//                                                                  nElement.appendChild(dspeech);
//                                                              }
//                                                          }
//
//                                                        }
                                                        // if speaker not found  create the element and append a speech
                                                       
                                                            Element omilitis =doc.createElement("Speaker"+ (titlos.getLength()+1) );
                                                            omilitis.setAttribute("speaker", list.get(i).name);
//                                                            System.out.println(list.get(i).speaker);
//                                                            System.out.println(list.get(i).speech);
                                                            toElement.appendChild(omilitis);
                                                            Element dspeech= doc.createElement("Speech");
                                                            dspeech.setAttribute("speech", list.get(i).speech);
                                                            omilitis.appendChild(dspeech);
                                                         
                                                    }
                                                    // if topic has no child nodes append the first child and the speech with it
                                                    else{
                                                        Element omilitis =doc.createElement("Speaker1");
                                                        omilitis.setAttribute("speaker", list.get(i).name);
                                                        toElement.appendChild(omilitis);
                                                        Element dspeech= doc.createElement("Speech");
                                                        dspeech.setAttribute("speech", list.get(i).speech);
                                                        omilitis.appendChild(dspeech);
                                                    }
                                                }

                                            }
                                            // if topic does not exist initialize a node and append it on thema, on that node also append the speaker and the speech
                                            if(Ftopic==0){
                                                Element etopic= doc.createElement("Topic"+(thema.getLength()+1));
                                                etopic.setAttribute("topic", list.get(i).topic);
                                                thElement.appendChild(etopic);
                                                Element omilitis =doc.createElement("Speaker1");
                                                omilitis.setAttribute("speaker", list.get(i).name);
                                                etopic.appendChild(omilitis);
                                                Element dspeech= doc.createElement("Speech");
                                                dspeech.setAttribute("speech", list.get(i).speech);
                                                omilitis.appendChild(dspeech);
                                            }
                                        }
                                        // if theme has no child nodes append the first child and all the extra info (speker, speech)
                                        else{

                                                Element etopic= doc.createElement("Topic1");
                                                etopic.setAttribute("topic", list.get(i).topic);
                                                thElement.appendChild(etopic);
                                                Element omilitis =doc.createElement("Speaker1");
                                                omilitis.setAttribute("speaker", list.get(i).name);
                                                etopic.appendChild(omilitis);
                                                Element dspeech= doc.createElement("Speech");
                                                dspeech.setAttribute("speech", list.get(i).speech);
                                                omilitis.appendChild(dspeech);
                                        }
                                    }

                                }
                                // if theme not found then append the theme and all extra info (topic, speaker, speech)
                                if (Ftheme==0){
                                Element etheme= doc.createElement("Theme"+ (rootchild.getLength()+1));
                                etheme.setAttribute("theme", list.get(i).theme);
                                rootElement.appendChild(etheme);   
                                Element etopic= doc.createElement("Topic1");
                                etopic.setAttribute("topic", list.get(i).topic);
                                etheme.appendChild(etopic);
                                Element omilitis =doc.createElement("Speaker1");
                                omilitis.setAttribute("speaker", list.get(i).name);
                                etopic.appendChild(omilitis);
                                Element dspeech= doc.createElement("Speech");
                                dspeech.setAttribute("speech", list.get(i).speech);
                                omilitis.appendChild(dspeech);
                                }


                            }
                            // if root has no child NOdes  append the first node and all the extra info (topic speaker speech)
                            else{
                                Element etheme= doc.createElement("Theme1");
                                etheme.setAttribute("theme", list.get(i).theme);
                                rootElement.appendChild(etheme);
                                Element etopic= doc.createElement("Topic1");
                                etopic.setAttribute("topic", list.get(i).topic);
                                etheme.appendChild(etopic);
                                Element omilitis =doc.createElement("Speaker1");
                                omilitis.setAttribute("speaker", list.get(i).name);
                                etopic.appendChild(omilitis);
                                Element dspeech= doc.createElement("Speech1");
                                dspeech.setAttribute("speech", list.get(i).speech);
                                omilitis.appendChild(dspeech);
                            }
                    }
                    }
                }
                // initialize transformer
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("/home/iasonas/Desktop/laptop/xmlProcedures/xml"+name+".xml"));
 
		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);
                //normalize the output so it is produced on different lines
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(source, result);
                // message for each succesfull xml production!
		System.out.println("File saved!");
 
	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
	}
}

