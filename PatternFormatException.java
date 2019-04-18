

public class PatternFormatException extends Exception{
		
    public PatternFormatException(){
            
    super("please specify the pattern!");
            
    }
            
    public PatternFormatException(String message){
            
    super(message);
            
    }

}