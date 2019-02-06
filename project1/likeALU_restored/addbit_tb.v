module addbit_tb();

reg a;
reg b;
reg cin;
wire out,cout;

addbit adder(out,cout,a,b,cin);

initial begin
$monitor("a =%b b =%b cin =%b out =%b cout =%b",a,b,cin,out,cout);
a =1'b0;
b =1'b0;
cin =1'b0;
#10
cin =1'b1;
#10
b =1'b1;
cin =1'b0;
#10
cin =1'b1;
#10
a =1'b1;
cin =1'b0;
b =1'b0;
#10
cin =1'b1;
#10
cin =1'b0;
b =1'b1;
#10
cin =1'b1;
#10 $finish;
end

endmodule

