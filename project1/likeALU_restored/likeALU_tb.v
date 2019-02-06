module likeALU_tb();

reg [4:0] inp_A;
reg [4:0] inp_B;
reg [1:0] select;
wire [4:0] out;

likeALU f(out,inp_A,inp_B,select);


initial begin 
 $monitor("inp_A =%b inp_B =%b select =%b result =%b",inp_A,inp_B,select,out);
 inp_A =5'b01001;
 inp_B =5'b00111;
 select =2'b00;
 #10
 select =2'b01;
 #10
 select =2'b10;
 #10
 select =2'b11;
 #10
 inp_A =5'b10101;
 inp_B =5'b01100;
 select =2'b00;
 #10
 select =2'b00;
 #10
 select =2'b01;
 #10
 select =2'b10;
 #10
 select =2'b11; 
 #10 $finish;
 
 end
 
 endmodule