package rogel.io.fopl;

public class Constant implements Unifiable 
{
	private static int nextID = 1;
	
	private int id;
	private String printName = null;
	
	public Constant() {
		this.id = Constant.nextID; //assign it a new ID
		Constant.nextID++;
	}
	
	public Constant(String printName) {
		this();
		this.printName = printName;
	}
	
	/**
	 * @return the id
	 */
	public int getID() {
		return id;
	}
	
	@Override
	public String toString() {
		if(this.printName != null) {
			return this.printName;
		}
		
		else {
			return "constant_"+this.id;
		}
	}
	
	@Override
	public boolean unify() {
		// TODO Auto-generated method stub
		return false;
	}	

}
