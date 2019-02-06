module likeALU(out,inp_A,inp_B,select);

input [4:0] inp_A;
input [4:0] inp_B;
input [1:0] select;
output [4:0] out;

wire [4:0] inp_A;
wire [4:0] inp_B;
wire [1:0] select;
wire [4:0] out;

// these are for adding operation
wire carry;
wire c0,c1,c2,c3;

select u0(out[0],c0,inp_A[0],inp_B[0],1'b0,select[1],select[0]);
select u1(out[1],c1,inp_A[1],inp_B[1],c0,select[1],select[0]);
select u2(out[2],c2,inp_A[2],inp_B[2],c1,select[1],select[0]);
select u3(out[3],c3,inp_A[3],inp_B[3],c2,select[1],select[0]);
select u4(out[4],carry,inp_A[4],inp_B[4],c3,select[1],select[0]);


endmodule

