package realgraffiti.android.data;

import java.util.ArrayList;
import java.util.Collection;

import realgraffiti.android.web.GraffitiPollListener;
import realgraffiti.common.data.RealGraffitiData;
import realgraffiti.common.dataObjects.Graffiti;
import realgraffiti.common.dataObjects.GraffitiLocationParameters;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class GraffitiPoller {
	private RealGraffitiData _realGraffitiData;
	private int _pollingInterval;
	private GraffitiPollListener _polllListener;
	private Context _context;
	private GraffitiPoll _graffitiPolltask;
	
	public GraffitiPoller(Context context, RealGraffitiData realGraffitiData, int pollingIntervapl){
		_realGraffitiData = realGraffitiData;
		_pollingInterval = pollingIntervapl;
		_context = context;
	}
	
	public RealGraffitiData getPolledRealGraffitiData(){
		return _realGraffitiData;
	}
	
	public void setOnPoll(GraffitiPollListener pollListener){
		_polllListener = pollListener;
	}
	
	public void beginPolling(){
		_graffitiPolltask = new GraffitiPoll();
		_graffitiPolltask.execute(_pollingInterval);
	}
	
	public void stopPolling(){
		_graffitiPolltask.cancel(true);
		_graffitiPolltask = null;
	}
	
	 private class GraffitiPoll extends AsyncTask<Integer, Collection<Graffiti>, Collection<Graffiti>> {
		 private boolean _running = true;
		 @Override
		protected Collection<Graffiti> doInBackground(Integer... params) {
			 Collection<Graffiti> graffities = null;
			 while(_running){
				 GraffitiLocationParametersGenerator locationParametersGenerator = 
					 GraffitiLocationParametersGeneratorFactory.getGaffitiLocationParametersGenerator(_context);
				 
				//Log.d("GraffitiPoll",  "Location Parameter Available (for GraffitiPoll): " + locationParametersGenerator.isLocationParametersAvailable());
				
				if(locationParametersGenerator.isLocationParametersAvailable()){
					GraffitiLocationParameters graffitiLocationParameters = locationParametersGenerator.getCurrentLocationParameters();
					graffities = _realGraffitiData.getNearByGraffiti(graffitiLocationParameters);
				
					publishProgress(graffities);
				}
				
				try {
					Thread.sleep(params[0]);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			 }	 
			return graffities;
			
		}
		 
		 @Override
        protected void onCancelled() {
           _running = false;
        }
		 
	     protected void onProgressUpdate(Collection<Graffiti>... graffities) {
	         _polllListener.onPollingData(graffities[0]);
	     }		
	 }
}


