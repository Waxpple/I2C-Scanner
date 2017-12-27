package rocket;
import static jssc.SerialPort.MASK_RXCHAR;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class SerialRead implements Runnable {

	
	private static SerialPort serialPort;
	public static int SerialFlag=0;
	
	public void run() {
		
		
		serialPort = new SerialPort(getPortName());
		//serialPort = new SerialPort("COM10");
        try {
        	
        	StringBuilder message = new StringBuilder();
			serialPort.openPort();
			serialPort.setParams(
			SerialPort.BAUDRATE_115200,
            SerialPort.DATABITS_8,
            SerialPort.STOPBITS_1,
            SerialPort.PARITY_NONE);
			
			serialPort.setEventsMask(MASK_RXCHAR);
			
            serialPort.addEventListener((SerialPortEvent serialPortEvent) -> {
            	if(GameStuff.flag) {
                if(serialPortEvent.isRXCHAR()){
                    try {
                        String st2 = serialPort.readString(10);
                        String[] st = st2.split("");
                       if(SerialFlag<1000) {
                        SerialFlag++;
                       }
                       for(int j=0;j<10;j++) {
                        
                        if(st[j].compareTo("@")==0) {
                        		if(message.length()!=0) {
                        			if(SerialFlag>=800) {
                        		Double k = Double.valueOf(message.toString());
                        		// System.out.println(i+":"+message.toString());
                        		 GameStuff.xPostion=k;
                        			}
                        			//System.out.println(i);
                        		}
	                       
	                        message.setLength(0);
	                        
                        }else {
                        	message.append(st[j]);
                        }
                       }
                        
                        
                    } catch (SerialPortException  ex) {
                        
                    }
                    
                }}else {
                	try {
						serialPort.closePort();
					} catch (SerialPortException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            });
            		
            		
            		
            		
            		
                
               // String b = serialPort.readString(10);
               // System.out.println(b);
                
                
		} catch (SerialPortException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
                


	}             
	
	public String getPortName() {
		String[] portNames = SerialPortList.getPortNames();
        for(int i = 0; i < portNames.length; i++){
            return portNames[0];
        }
        if(portNames.length==0) {
        	return "";
        }else {
        	return "error";
        }

	}
	public void discon() {
		try {
			GameStuff.flag=false;
			serialPort.closePort();
			
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
