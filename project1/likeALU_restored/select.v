module select(result,cout,inA,inB,cin,s1,s0); // cout and cin for add operation

input inA;
input inB;
input cin;
input s1;
input s0;

output result;
output cout;

wire result,cout;
wire inA,inB,cin,s1,s0;
wire notS0;
wire notS1;
wire y0,y1,y2,y3;
wire out0,out1,out2,out3;

not u0(notS0,s0);
not u1(notS1,s1);

// all operations
and f(y0,inA,inB);
addbit g(y1,cout,inA,inB,cin);
or h(y2,inA,inB);
xor j(y3,inA,inB);

// mux 
// s1 s0 00 and  01 add  10 or  11 xor 
and u2(out0,y0,notS1,notS0);
and u3(out1,y1,notS1,s0);
and u4(out2,y2,s1,notS0);
and u5(out3,y3,s1,s0);

or u6(result,out0,out1,out2,out3);

endmodule
