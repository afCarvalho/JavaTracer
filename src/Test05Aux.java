import ist.meic.pa.Trace;

class Test05Aux {
	
    private boolean teste = false;
    
    private static Test05Aux ola(Test05Aux arg){
    	return arg;
    }
    
    public static Test05Aux auxOutraClasse(Test05Aux arg){
    	return ola(arg);
    }
    
    public static void main(String args[]) {
        System.out.println("Teste Aux");
    }
}