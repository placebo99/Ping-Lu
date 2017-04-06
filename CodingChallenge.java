import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class CodingChallenge {	
	public static void main(String[] args) throws IOException{
		
		//First step, read the text file and save the data as an arraylist
		
		ArrayList<String> list = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader("C:\\Insight\\log.txt"));
		while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            list.add(line);
        }
        reader.close();
        
        int n = list.size();
        System.out.println(n);        
        
        //split each line into five components or attributes(columns), save each attribute as an array
        
        String[] host = new String[n]; 
        String[] timestamp = new String[n]; 
        String[] request = new String[n]; 
        String[] reply_code = new String[n]; 
        String[] bytes = new String[n]; 
        int i, j, k, m, p;
        for (i = 0; i < list.size(); i++){
        	String lineforsplit = list.get(i);
        	for (j = 0; j < lineforsplit.length(); j++ ){
        		if (lineforsplit.charAt(j)== ' '){
        			host[i] = lineforsplit.substring(0,j);
        			
        		break;
        		}
        	}
        	        	
        	for (k = j+6; k < lineforsplit.length(); k++){
        		if (lineforsplit.charAt(k)== ']'){
        			timestamp[i] = lineforsplit.substring(j+5, k+1);
        			break;
        		}
        	}
        	for (m = k+3; m < lineforsplit.length(); m++){ 
        		if (lineforsplit.charAt(m)== '"'){        			
        			request[i] = lineforsplit.substring(k+2, m+1);
                	
                	break;
        		}
        	}
        	
        	for (p = m+2; p < lineforsplit.length(); p++){
        		if (lineforsplit.charAt(p)== ' '){
        			reply_code[i] = lineforsplit.substring(m+2, p);
        			bytes[i] = lineforsplit.substring(p+1,lineforsplit.length());
        			break;
        		}
        	}
        }
        
        //Feature 1
        
        String[] hostlist = new String[host.length];
        
        for (int e =0; e < host.length; e++){
        	hostlist[e] = host[e];        	       	
        }   
        
        ArrayList<String> hostnodup = new ArrayList<>();
        ArrayList<Integer> appearnumbers = new ArrayList<>();            
        
        int a = 0;
        for (int x = 0; x < hostlist.length; x++){
        	if (hostlist[x] == "duplicated"){
        		continue;
        	}
        	int b = 1;
        	for (int y = x+1; y < hostlist.length; y++){
        		if (hostlist[y].equals(hostlist[x])){
        			hostlist[y] = "duplicated";
        			b++;        		        			
        		}
        	}
        	hostnodup.add(a, hostlist[x]);
        	appearnumbers.add(a, b);
        	a++;        	        	        	
        }
        
        //sort the result
        
        int tempNum;
        String tempHost;
        for (int u = 0; u < hostnodup.size(); u++){        	       	
        	for (int v = u+1; v < hostnodup.size(); v++){
        		if (appearnumbers.get(u) < appearnumbers.get(v)){
        			tempNum = appearnumbers.get(u);
        			appearnumbers.set(u, appearnumbers.get(v));
        			appearnumbers.set(v, tempNum);
        			tempHost = hostnodup.get(u);
        			hostnodup.set(u, hostnodup.get(v));
        			hostnodup.set(v, tempHost);        			
        		}
        		if (appearnumbers.get(u) == appearnumbers.get(v)){
        			int result = hostnodup.get(u).compareTo(hostnodup.get(v));
        			if (result < 0){
        				tempHost = hostnodup.get(u);
            			hostnodup.set(u, hostnodup.get(v));
            			hostnodup.set(v, tempHost);  
        			}
        		}        		
        	}
        }
        
        for(int s = 0; s < 10; s++) {   
            System.out.println(hostnodup.get(s)+","+appearnumbers.get(s));                        
        }       
        
        
        //Feature 2
        
        int[] bytesnum = new int[n];
        for (int h = 0; h < n; h++){
        	if (bytes[h].equals("-")){
        		bytes[h] = "0";
        	}
        }
        for (int l = 0; l < n; l++){
        	bytesnum[l] = Integer.parseInt(bytes[l]);
        }
        
        ArrayList<String> resources = new ArrayList<>();
        ArrayList<Integer> bytes2 = new ArrayList<>(); 
        for (int d = 0; d < n; d++){
        	if (request[d].substring(1, 4).equals("GET")){
        		for (int g = 5; g < request[d].length(); g++){
        			if (request[d].charAt(g) == ' '){
        				resources.add(request[d].substring(5, g));
        				bytes2.add(bytesnum[d]);        				
        			}
        		}
        	}
        }
        
        ArrayList<String> resourcesnodup = new ArrayList<>();
        ArrayList<Integer> bytesnodup = new ArrayList<>();   
        int r = 0;
        for (int x = 0; x < resources.size(); x++){
        	if (resources.get(x) == "duplicated"){
        		continue;
        	}
        	int b = bytes2.get(x);
        	for (int y = x+1; y < resources.size(); y++){
        		if (resources.get(y).equals(resources.get(x))){
        			resources.set(y, "duplicated");
        			b = b + bytes2.get(y);        			    		        			
        		}
        	}
        	resourcesnodup.add(r, resources.get(x));
        	bytesnodup.add(r, b);
        	r++;        	        	        	
        }
        
        //sort the result
        
        String tempResources;
        int tempBytes;
        for (int u = 0; u < resourcesnodup.size(); u++){        	       	
        	for (int v = u+1; v < resourcesnodup.size(); v++){
        		if (bytesnodup.get(u) < bytesnodup.get(v)){
        			tempBytes = bytesnodup.get(u);
        			bytesnodup.set(u, bytesnodup.get(v));
        			bytesnodup.set(v, tempBytes);
        			tempResources = resourcesnodup.get(u);
        			resourcesnodup.set(u, resourcesnodup.get(v));
        			resourcesnodup.set(v, tempResources);        			
        		}
        		if (bytesnodup.get(u) == bytesnodup.get(v)){
        			int result = resourcesnodup.get(u).compareTo(resourcesnodup.get(v));
        			if (result < 0){
        			tempResources = resourcesnodup.get(u);
        			resourcesnodup.set(u, resourcesnodup.get(v));
        			resourcesnodup.set(v, tempResources);
        			}
        		}
        	}
        }
        
        for(int s = 0; s < 10; s++) {   
            System.out.println(resourcesnodup.get(s)+","+bytesnodup.get(s));                        
        }       
                
        
        //Feature 3        
        
        String[] timestring = new String[n];
        for (int s = 0; s < n; s++){
        	timestamp[s] = timestamp[s].replaceAll("-400", "-0400");   
        	timestring[s] = timestamp[s].substring(1, 27);        	
        }
        
        Calendar[] time = new Calendar[n];
        Calendar[] time_60 = new Calendar[n];
        String pattern = "dd/MMM/yyyy:hh:mm:ss Z";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {          
        	for (int s = 0; s < n; s++){
        		Date date = format.parse(timestring[s]);
        		Calendar cal = Calendar.getInstance();
        		Calendar cal2 = Calendar.getInstance();
        		cal.setTime(date);
        		time[s] = cal;        		
        		cal.add(Calendar.MINUTE, 60);
        		cal2.setTime(cal.getTime());
        		time_60[s] = cal2;               		
        	}    
        } catch (ParseException e) {
          e.printStackTrace();
        }        
        int[] accessnum = new int[n];
        for (int s = 0; s < n; s++){
        	int access = 1;
        	for (int w = s+1; w < n; w++){
        		if (time[w].compareTo(time_60[s]) > 0){
        			access++;
        		}
        		else{        			
        			break;
        		}
        	}  
        	accessnum[s] = access;
        }   
        
        String tempTimestring;
        Calendar tempTime;
        int tempAccessnum;
        for (int u = 0; u < n; u++){        	       	
        	for (int v = u+1; v < n; v++){
        		if (accessnum[u] < accessnum[v]){
        			tempAccessnum = accessnum[u];
        			accessnum[u]= accessnum[v];
        			accessnum[v] = tempAccessnum;
        			tempTime = time[u];
        			time[u] = time[v];
        			time[v] = tempTime;
        			tempTimestring = timestring[u];
        			timestring[u] = timestring[v];
        			timestring[v] = tempTimestring;
        			      			
        		}
        		if (accessnum[u] == accessnum[v]){
        			int result = timestring[u].compareTo(timestring[v]);
        			if (result < 0){
        			tempTimestring = timestring[u];
        			timestring[u] = timestring[v];
        			timestring[v] = tempTimestring;
        			}
        		}
        	}
        }
        
        for(int s = 0; s < 10; s++) {   
            System.out.println(timestring[s]+","+accessnum[s]);                        
        }    
        
        
        //Feature 4
        
        //The HTTP reply code for failed login is 401
        //The HTTP reply code for successful login is 200
        
       
        ArrayList<String> host_checked = new ArrayList<>();
        ArrayList<Integer> index_blocked = new ArrayList<>();
        
        loop1:
        for (int s = 0; s < n; s++){
        	if (reply_code[s].equals("401")){      
        		if (host_checked.contains(host[s])){
        			continue;
        		}
        		host_checked.add(host[s]);
        		int failnum = 1;
        		Calendar c1 = time[s];
        		c1.add(Calendar.SECOND, 20);
        		Calendar c2 = Calendar.getInstance();
        		c2.setTime(c1.getTime());    		
        		
        		
        		int z;
        		loop2:
        		for (int t = s+1; t < n; t++){
        			if (host[t].equals(host[s])&& reply_code[t].equals("401")){
        				if (time[t].compareTo(c2) > 0){
        					failnum++;
        					if (failnum < 3){
        						continue;
        					}
        					else{
        						Calendar starting = time[t];
        						starting.add(Calendar.MINUTE, 5);
        						Calendar ending = Calendar.getInstance();
        						ending.setTime(starting.getTime());       						
        						
        						loop3:       						
        						for (z = t+1; z < n; z++){
        							if (time[z].compareTo(ending) > 0 && host[z].equals(host[t])){
        								index_blocked.add(z);        								
        							}        							
        						}
        						        						
        						loop4:
        						for (int y = z+1; y < n; y++){
        							if (host[y].equals(host[t])&& reply_code[y].equals("401")){
        								failnum = 1;
        	        					c1 = time[y];
        	        					c1.add(Calendar.SECOND, 20);
        	        	        		c2 = Calendar.getInstance();
        	        	        		c2.setTime(c1.getTime());  
        	        	        		s = z-1;
        	        	        		continue loop2;        	        	        		
        							}
        						}
        					}
        				}
        				else{
        					failnum = 1;
        					c1 = time[t];
        					c1.add(Calendar.SECOND, 20);
        	        		c2 = Calendar.getInstance();
        	        		c2.setTime(c1.getTime());  
        	        		s = t;
        	        		continue;
        				}
        			}        			
        			if (host[t].equals(host[s]) && reply_code[t].equals("200")){
        					failnum = 0;
        					        					        					
        					loop5:
        					for (int u = t+1; u < n; u++){
        						if (reply_code[u].equals("401") && host[u].equals(host[t])){
        							failnum = 1;
        							c1 = time[u];
        							c1.add(Calendar.SECOND, 20);
        							c2 = Calendar.getInstance();
        			        		c2.setTime(c1.getTime());
        			        		s = u;
        			        		continue loop2;        			        		
        						}
        					}
        				}
        			}
        		}
        	}
        for (int s = 0; s < index_blocked.size(); s++){
        	System.out.println(list.indexOf(index_blocked.get(s)));        	
        }
    }        
}







