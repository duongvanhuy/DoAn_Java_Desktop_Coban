/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author dell
 */
public class demo {
    public static int giaiThua(int a){
        int k =1;
        for( int l =2; l<= a ; l++){
                    k *= l;
                }
        return   k;
    }
    
    
    public static void  toHop(){

//        int a = n-2;
int a = giaiThua(14);
        System.out.println("giai thừa" +giaiThua(16) + " mẫu: " + (a));
//        return giaiThua(n)/(2*giaiThua(n-2));
    }
    public static void main(String[] args) {
//         n là k* k
        int n =6;
//        int k = n*n;
        int result =0;
         int a =1;
        for( int i =0; i< n ; i ++){
            for(int j =0; j< n; j++){
                
                if((i +2)<n  && (j+1)<n){
                    result ++;   
                }
                 if((i +1)<n  &&(j+2)<n ){
                    result ++;
                
                }
                 if((i + 2)<n  &&(j-1)>=0 ){
                    result ++;
                
                }
                 if((i +1)<n  &&(j -2)>=0 ){
                    result ++;
                
                }
                 if((i -2)>=0  &&(j +1)<n ){
                    result ++;
                
                }
                 if((i -2)>=0  &&(j-1)>=0 ){
                    result ++;
                
                }
                 if((i -1)>=0  && (j+2)<n ){
                    result ++;
                
                }
                 if((i -1)>=0  &&(j-2)>=0 ){
                    result ++;
                
                }
                
            }
        }
       long p =  giaiThua(16)/(2*giaiThua(14));
        System.out.println( "giai thưa: " +giaiThua(4));
//        System.out.println(toHop(k));
        System.out.println(p);
        System.out.println("aaa: " + result);
toHop();
    }
}
