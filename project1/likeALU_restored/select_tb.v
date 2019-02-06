module select_tb();

reg inA, inB,cin,s1,s0;
wire out,cout;

select S(out,cout,inA,inB,cin,s1,s0);

initial begin 
$monitor("inA =%b inB =%b cin =%b s1 =%b s0 =%b out =%b cout =%b",inA,inB,cin,s1,s0,out,cout);
inA =1'b0;
inB =1'b0;
cin =1'b0;
s1 =1'b0;
s0 =1'b0;
#10
inA =1'b1;
#10
inB =1'b1;
#10
s0 =1'b1;
#10
s1 =1'b1;
s0 =1'b0;
#10
s0 =1'b1;
#10
inA =1'b0;
#10
s1 =1'b0;
s0 =1'b0;
#10 $finish;
end

endmodule
