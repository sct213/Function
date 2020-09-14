// 인터페이스는 구현과 상속을 모두 할 수 있다.
// 구현부가 없으므로 인터페이스를 만든다면 반드시 구현하는 클래스를 만들어야
// 하며, 인터페이스를 구현하기로한 클래스는 반드시 인터페이스에 명시되어 있는 추상메서드들을 모두 구현해야 한다. 만약 이를 구현하지 않으면 컴파일 에러가 발생한다.
public interface Walkabel {
	void walk();
}

public interface Flyable {
	void fly();
}

public interface Moveable extends Walkable, Flyable {
}

public class interface_theroy implements Moveable {
	@Override
	public void walk() {
		System.out.println("walk");
	}
	
	@Override
	public void fly() {
		System.out.println("fly");
	}
}