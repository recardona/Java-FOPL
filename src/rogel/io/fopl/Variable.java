package rogel.io.fopl;

public class Variable implements Unifiable 
{
	private static int nextID = 1;
	
	private int id;

	private String printName = null;
	
	public Variable() {
		this.id = Variable.nextID; //assign it a new id
		Variable.nextID++;
	}
	
	public Variable(String printName) {
		this();
		this.printName = printName;
	}
	
	public Variable(Variable v) {
		this();
		this.printName = v.printName;
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
			return this.printName + "_" + this.id;
		}
		
		return "V"+this.id;
	}
	
	@Override
	public boolean unify() {
		// TODO Auto-generated method stub
		return false;
	}

}
