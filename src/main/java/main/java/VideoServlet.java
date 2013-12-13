package main.java;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "video",urlPatterns = {"/video/*"})
@MultipartConfig
public class VideoServlet extends HttpServlet {
	
	
	private static final long serialVersionUID = 6872672185158998617L;

	/**
	 *For some video-files data 
	 */	
	private class VideoString{
		public VideoString(String name, String url) {
			super();
			this.name = name;
			this.url = url;
		}
		String name;
		String url;
	}
	
	/**
	 *Video files data container 
	 */
	private List<VideoString> videos = new ArrayList<VideoString>();

    /**
     * Fill video files data container
     */
	public void init(){
    	videos.add(new VideoString("Cats","Funny cats.flv"));
    	videos.add(new VideoString("Cars","Bentley Continental GT V8 Launch Film First Commercial - Carjam TV HD Car TV Show 2013 - YouTube.flv"));
    	videos.add(new VideoString("Girls","Vertigo Cheerleading Team - YouTube.flv"));
    }
    
    /**
     * 
     * @param baseUrl request.getRequestURL().toString()
     * @return html part with right link to each video file
     */
	private String generateLinks(String baseUrl){
    	String answer="";
    	for(VideoString v: videos){
    		answer = answer+
    				"<a href=\""+
    				baseUrl+"?video="+
    				videos.indexOf(v)+
    				"\">"+
    				v.name+
    				"</a><br/>";
    	}
    	return answer;
    }
	
	public void service(HttpServletRequest request, 
      HttpServletResponse response)
              throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		String parameter = request.getParameter("video");
		
		if(parameter==null){ //title page    	 
         writer.println(ServletUtilities.headWithTitle("Video WWW") +
                 "<BODY>\n" +
                 "<H1>Links</H1>\n" +
                 generateLinks(request.getRequestURL().toString())+                                   
                 "</BODY></HTML>");
         writer.close();
		}
		else{ //generate video page
			try{
				writer.println(ServletUtilities.headWithTitle("Video WWW") +
		                 "<script type=\"text/javascript\" src=\""+request.getContextPath() + "/js/flowplayer-3.2.6.min.js\"></script><BODY>\n" +
		                 "<H1>"+
		                 videos.get(Integer.parseInt(parameter)).name+
		                 "</H1>\n"+ 
		                 "<br/>"		                 
		                 + "<a href=\""+request.getContextPath() + "/videos/"+
		                 videos.get(Integer.parseInt(parameter)).url+
		    				"\" style=\"display:block;width:425px;height:300px;\" id=\"player\"></a><br/>"+
		    				"<script>flowplayer(\"player\", \""+request.getContextPath() + "/js/flowplayer-3.2.7.swf\");</script>"+
		    				"<a href=\""+
			                 request.getRequestURL().toString()+
			    				"\">"+
			    				"Back to list"+
			    				"</a><br/>"+
		                 "</BODY></HTML>");
		         writer.close();
			}
			catch (Exception e){ //no video 
				writer.println(ServletUtilities.headWithTitle("Video WWW") +
		                 "<BODY>\n" +
		                 "<H1>Links</H1>\n" +
		                 "Video doesn't exists."+  
		                 "<br/><a href=\""+
		                 request.getRequestURL().toString()+
		    				"\">"+
		    				"Back to list"+
		    				"</a><br/>"+
		                 "</BODY></HTML>");
		         writer.close();
			}	         
		}         
    }
}

