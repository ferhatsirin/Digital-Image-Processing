module addbit(sum,co,a,b,ci); 
// input output port declaration
input a;
input b;
input ci;
output sum;
output co;
// type declaration
wire a;
wire b;
wire ci;
wire out1,out2,out3;

wire sum;
wire co;

xor f(out1,a,b);
xor g(sum,out1,ci);
and h(out2,a,b);
and j(out3,out1,ci);
or k(co,out2,out3);

endmodule
