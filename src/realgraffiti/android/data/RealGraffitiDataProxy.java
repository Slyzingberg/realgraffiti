package realgraffiti.android.data;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import android.content.Context;
import android.os.Debug;
import android.util.Log;

import realgraffiti.android.R;
import realgraffiti.android.data.RestClient.RequestMethod;
import realgraffiti.common.data.RealGraffitiData;
import realgraffiti.common.dto.GraffitiDto;
import realgraffiti.common.dto.GraffitiLocationParametersDto;

public class RealGraffitiDataProxy implements RealGraffitiData{
	private Context _context;
	
	private final String ACTION_KEY = "action";
	private final String ACTION_PARAMETER_KEY = "object";
	
	public RealGraffitiDataProxy(Context context){
		_context = context;
	}
	
	@Override
	public boolean addNewGraffiti(GraffitiDto graffitiDto){	
		String uploadUrl = getUploadUrl();
		Log.d("realgraffiti", "upload url: " + uploadUrl);
		
		RestClient client = new RestClient(uploadUrl);
		client.addParam(ACTION_KEY, _context.getString(R.string.addGraffiti));
		client.addParam("object", graffitiDto);
		client.addFile("file", graffitiDto.get_imageData());
		
		client.execute(RequestMethod.POST);
		
		int responseCode = client.getResponseCode();
		String response = client.getResponse();
		
		Log.d("realgraffiti", "request reponse: " + response);
		return responseCode == HttpURLConnection.HTTP_OK;
	}

	private String getUploadUrl(){
		String serverPath = _context.getString(R.string.ServerPath);
		String url = serverPath + "/" +  _context.getString(R.string.serverInfoServlet);
		String action = _context.getString(R.string.getUploadUrlAction);
		RestClient client = new RestClient(url);
		client.addParam("action", action);
		
		client.execute(RequestMethod.POST);
		
		String uploadUrl = client.getResponse();
		
		return serverPath + uploadUrl.trim();
	}
	
	@Override
	public Collection<GraffitiDto> getNearByGraffiti(
			GraffitiLocationParametersDto graffitiLocationParameters) {
		String url = _context.getString(R.string.ServerPath);
		url += "/" + _context.getString(R.string.RealGraffitiDataServlet);
		
		RestClient client = new RestClient(url);
		String actionName = _context.getString(R.string.getNearByGraffiti);
		client.addParam(ACTION_KEY, actionName);
		client.addParam(ACTION_PARAMETER_KEY, graffitiLocationParameters);
		
		client.execute(RestClient.RequestMethod.POST);
	
		ArrayList<GraffitiDto> test = new ArrayList<GraffitiDto>();
		
		Collection<GraffitiDto> nearByGraffiti = (ArrayList<GraffitiDto>)client.getResponseObject( test.getClass());
		return nearByGraffiti;		
	}
	
	  public class SkipTypeStrategy implements ExclusionStrategy {
		    private final Class<?> typeToSkip;
	
		    private SkipTypeStrategy(Class<?> typeToSkip) {
		      this.typeToSkip = typeToSkip;
		    }
	
		    public boolean shouldSkipClass(Class<?> clazz) {
		      return false;
		    }
	
		    public boolean shouldSkipField(FieldAttributes f) {
		      return f.getClass().equals(typeToSkip);
		    }
		  }
}


