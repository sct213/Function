import java.util.Scanner;
// if문을 이용한 10진수 15까지 2진수로 변환기
class Binary1111 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		String num = sc.nextLine();
		
		int test = Integer.parseInt(num);
		
		if(test < 999){
			System.out.print("네자리수까지 입력해주세요 (예: 0001) : ");

			num = sc.nextLine();
		}
		
		String num1 = num.substring(0, 1);
		String num2 = num.substring(1, 2);
		String num3 = num.substring(2, 3);
		String num4 = num.substring(3, 4);
		
		int n1 = Integer.parseInt(num1);
		int n2 = Integer.parseInt(num2);
		int n3 = Integer.parseInt(num3);
		int n4 = Integer.parseInt(num4);
		
		if(n1 == 0){
					if(n2 == 0){
								if(n3 == 0){
										if(n4 == 0){
											System.out.println("0");
										} else {
											System.out.println("1");
										}
								} else {
									if(n4 == 0){
										System.out.println("2");
									} else {
										System.out.println("3");
									}
								}
					} else {
						if(n3 == 0){
							if(n4 == 0){
								System.out.println("4");
							} else {
								System.out.println("5");
							}
						} else {
							if(n4 == 0){
								System.out.println("6");
							} else {
								System.out.println("7");
							}
						}
					}
		} else {
			if(n2 == 0){
						if(n3 == 0){
								if(n4 == 0){
									System.out.println("8");
								} else {
									System.out.println("9");
								}
						} else {
							if(n4 == 0){
								System.out.println("10");
							} else {
								System.out.println("11");
							}
						}
			} else {
				if(n3 == 0){
					if(n4 == 0){
						System.out.println("12");
					} else {
						System.out.println("13");
					}
				} else {
					if(n4 == 0){
						System.out.println("14");
					} else {
						System.out.println("15");
					}
				}
			}
		}
 		

	}
}
/*
0000 0
0001 1
0010 2
0011 3
0100 4
0101 5
0110 6
0111 7
1000 8
1001 9
1010 10
1011 11
1100 12
1101 13
1110 14
1111 15
*/