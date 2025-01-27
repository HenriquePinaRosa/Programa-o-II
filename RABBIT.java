public class RABBIT {
    
    private int age = 1; //coelho nasce com 0 e morre quando age = starvetime + 1

    public RABBIT(){}

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age = this.age + age;
    }

}
