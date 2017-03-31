package dk.vonfrank.ou44locationservice.Util;

import com.kontakt.sdk.android.common.profile.IBeaconDevice;

import java.util.ArrayList;

import dk.vonfrank.ou44locationservice.Models.BeaconItem;

/**
 * Created by Von Frank on 29-03-2017.
 */

public class AppData {
    private ArrayList<BeaconItem> beaconItems;
    private ArrayList<IBeaconDevice> iBeaconDevices;

    private static final AppData appData = new AppData();

    public static AppData getInstance(){
        return appData;
    }

    public ArrayList<BeaconItem> getBeaconItems(){
        return this.beaconItems;
    }

    public void setBeaconItems(ArrayList<BeaconItem> beaconItems){
        this.beaconItems = beaconItems;
    }

    public void addIBeaconDevice(IBeaconDevice iBeaconDevice){
        if(iBeaconDevices != null){
            iBeaconDevices = new ArrayList<IBeaconDevice>();
        }

        iBeaconDevices.add(iBeaconDevice);
    }

    public void removeIBeaconDevice(IBeaconDevice iBeaconDevice){
        for(IBeaconDevice ibd : iBeaconDevices){
            if(ibd.getUniqueId().equals(iBeaconDevice)){
                iBeaconDevices.remove(ibd);
            }
        }
    }

    public Boolean checkForDevice(IBeaconDevice iBeaconDevice){
        if(iBeaconDevices == null){
            iBeaconDevices = new ArrayList<IBeaconDevice>();
        }

        for(IBeaconDevice ibd : iBeaconDevices){
            if(ibd.getUniqueId().equals(iBeaconDevice.getUniqueId())){
                ibd = iBeaconDevice;
                return true;
            }
        }

        return false;
    }

    public ArrayList<IBeaconDevice> getiBeaconDevices(){
        return this.iBeaconDevices;
    }

    public void setiBeaconDevices(ArrayList<IBeaconDevice> iBeaconDevices){
        this.iBeaconDevices = iBeaconDevices;
    }

    public IBeaconDevice getIBeaconDeviceWithHighestRSSI(){
        IBeaconDevice temp = null;
        for(IBeaconDevice ibd : iBeaconDevices){
            System.out.println(ibd.getUniqueId() + ": " + ibd.getRssi());
            if(temp != null){
                if(ibd.getRssi() < temp.getRssi()){
                    temp = ibd;
                }
            } else {
                temp = ibd;
            }
        }
        return temp;
    }

    public BeaconItem getBeaconData(IBeaconDevice iBeaconDevice){
        for(BeaconItem bi : beaconItems){
            if(bi.getAlias().equals(iBeaconDevice.getUniqueId())){
                return bi;
            }
        }
        return null;
    }


}
