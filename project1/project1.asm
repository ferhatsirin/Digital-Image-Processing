	.data
msg1:	.asciiz "Enter the operation :"
msgEr:  .asciiz "Invalid input!!!"
dot: .asciiz "."
input:	.space 100
num1:   .word 0
num2:	.word 0
point: .word 0
operator: .space 1

	.text
	.globl main

main:	
	li $v0, 4    # printf("Enter the operation");
	la $a0, msg1
	syscall

	li $v0, 8
	la $a0, input	# scanf("%s",input);
	li $a1, 100
	syscall

	la $a0, input    # call parse function
	jal Parse

	addi $t0, $zero, 43  # $t0 ='+'
	addi $t1, $zero, 45  # $t1 ='-'
	addi $t2, $zero, 42  # $t2 ='*'

	lw $s1, num1     # $s1 =num1
	lw $s2, num2     # $s2 =num2
	lw $s3, point    # $s3 =point -> how many numbers after decimal point
	lb $s4, operator # $s4 =operator
	li $s5, 0
	li $s6, 0  # $s5 and $s6 for result $s5 before $s6 after decimal point

	beq $s4, $t0, sum   # if($s4 == '+') goto sum
	beq $s4, $t1, subtract  # if($s4 == '-') goto subtract
	beq $s4, $t2, multiply # if($s4 =='*') goto multiply
 
	sum: move $a0, $s1
	     move $a1, $s2
	     jal Add         # calling Add function to add two number
             move $s5, $v0   # result in $s5
             j putPoint

	subtract: move $a0, $s1
	     move $a1, $s2
	     jal Sub        # calling sub fuction to subtract two number
             move $s5, $v0  # result in $s5
	     j putPoint 

	multiply:  move $a0, $s1
	           move $a1, $s2
	           jal Mult   # calling mult func to multiply two number
                   move $s5, $v0  # result in $s5
                   j putPoint

	putPoint:     # after operation seperating the number according to  the point number
		  move $t0, $zero    #for count               #$s5 is the number before decimal point  $s6 is after decimal point
		  addi $t1, $zero, 10               # for(i=0;i<point;++i)
		  PLoop: beq $t0, $s3, fixSign      #    $s6 +=($s5%10)*power(i);
		    	 div $s5, $t1               #    $s5 =$s5/10;
			 mfhi $t2                   
			 mflo $s5 
			 move $a0, $t0
			 jal Power        # power func return 10^$t0
			 mult $v0, $t2
			 mflo $t2
			 add $s6, $s6, $t2
			 addi $t0, $t0, 1
	                 j PLoop
	
	fixSign: slt $t0, $s6, $zero    # if($s6 <0)  # s6 is the number after decimal point
		 beq $t0, $zero, Print	 # $s6 *=-1;
		 addi $t0, $zero, -1     
		 mult $s6, $t0
		 mflo $s6

	Print: li $v0, 1      # printf("%d.%d",$s5,$s6);
	       move $a0, $s5   
	       syscall

	       li $v0, 4    
 	       la $a0, dot
	       syscall

	       li $v0, 1
   	       move $a0, $s6
	       syscall

Exit:	li $v0, 10  # exit(1);
	syscall

Add: add $v0, $a0, $a1
     jr $ra

Sub: sub $v0, $a0, $a1
     jr $ra

Mult: mult $a0, $a1
      mflo $v0
      jr $ra

Parse:            #void parse($a0=&input); assign result to num1, num2, operator, point

	move $s0, $a0  # $s0 =&input
	
	li $t0, 10 # newline character end of string
	li $t1, 46 # point '.'
	li $t2, 43 # plus '+'
	li $t3, 45 # minus '-'
	li $t4, 42 # cross '*'
	li $t5, 32 # space ' '

	li $s1, 0   # this for num1
        li $s2, 0   # this for num1's fractional part count1 
	li $s3, 1   # this is num1Sign positive at default
	li $s4, 0   # this for num2
	li $s5, 0   # this for num2's fractional part count2
        li $s6, 1   # this is num2Sign positive at default
	li $s7, 0   # this is for operator

        move $a0, $s0
        addi $sp, $sp, -4    #saving $ra not to lose  return address 
        sw $ra, 0($sp)
	jal takeSign   # if number1 is negative
      	lw $ra, 0($sp)
        addi $sp, $sp, 4
	move $s0, $v0   # &input[i]
	move $s3, $v1  # num1Sign 

	Num1Loop1: lb $t6, 0($s0)               # for(input[i] != '\n' ' ' '+' '-' '*' '.')
	       beq $t6, $t0 PErrorExit           # if(input[i] =='\n') goto error
	       beq $t6, $t1, fractionalPart1	#  if(input[i] =='.') goto fract. 
	       beq $t6, $t2, takeOperator       #  if(input[i] == '+' || '-' || '*') goto takeOp.
	       beq $t6, $t3, takeOperator
	       beq $t6, $t4, takeOperator
	       beq $t6, $t5, space                   
	       slti $t7, $t6, 48         #  if(input[i]<48 || 57 < input[i]) 
	       bne $t7, $zero PErrorExit	# if means char is different from number then goto error
	       slti $t7, $t6, 58
	       beq $t7, $zero, PErrorExit
	       addi $t6, $t6, -48        # char -48 to int
	       li $t7, 10
	       mult $t7, $s1             
	       mflo $t7          
	       add $s1, $t7, $t6        # num1 =(input[i]-48)+num1*10
	       addi $s0, $s0, 1   # input[++i]
	       j Num1Loop1
	       
	fractionalPart1:
		 addi $s0, $s0, 1 #input[++i]
	        Num1Loop2: lb $t6, 0($s0)           # for(input[i] != '\n' ' ' '+' '-' '*')             
                        beq $t6, $t0 PErrorExit   # if(input[i] =='\n') goto error
	                beq $t6, $t5, space        # if(input[i] ==' ') goto space
	                beq $t6, $t2, takeOperator   #  if(input[i] == '+' || '-' || '*') goto takeOp.
	                beq $t6, $t3, takeOperator
	                beq $t6, $t4, takeOperator
               	        slti $t7, $t6, 48         #  if(input[i]<48 || 57 < input[i])
	                bne $t7, $zero PErrorExit   # if means char is different from number then goto error
	                slti $t7, $t6, 58
	                beq $t7, $zero, PErrorExit
	                addi $t6, $t6, -48        # char -48 to int
	                li $t7, 10
	                mult $t7, $s1
	                mflo $t7
	                add $s1, $t7, $t6    # num1 =(input[i]-48)+num1*10
	                addi $s0, $s0, 1   # input[++i]
		        addi $s2, $s2, 1  # ++count1
	                j Num1Loop2

	space: addi $sp, $sp, -4   # to pass spaces
	       sw $ra, 0($sp)  #saving $ra to sp not to lose return adress 
               move $a0, $s0  
	       jal PassSpace
	       move $s0, $v0
	       lw $ra, 0($sp)
	       addi $sp, $sp, 4
	
	takeOperator: lb $s7, 0($s0)     #  saving operator to $s7
		      addi $s0, $s0, 1  # input[++i]
                      addi $sp, $sp, -4
	              sw $ra, 0($sp)
		      move $a0, $s0
		      jal PassSpace    # passSpace after operator
		      move $s0, $v0
		      lw $ra, 0($sp)
	              addi $sp, $sp, 4

		      beq $s7, $t2, Num2Sign   # if(operator == '+')
		      beq $s7, $t3, Num2Sign   # if (operator =='-')
		      beq $s7, $t4, Num2Sign   # if(operator =='*')
		      j PErrorExit  # if not '+' '-' '*' then goto error

	Num2Sign: move $a0, $s0
                  addi $sp, $sp, -4    #saving $ra not to lose return address
	   	  sw $ra, 0($sp)
	          jal takeSign   # if number1 is negative
	 	  move $s0, $v0  #input[i]
		  move $s6, $v1  # num2Sign
		  lw $ra, 0($sp)
	          addi $sp, $sp, 4

	Num2Loop1: lb $t6, 0($s0)               # for(input[i] != '\n' '.')
      	           beq $t6, $t0 PointPart
	           beq $t6, $t1, fractionalPart2
	           slti $t7, $t6, 48         #  if(input[i]<48 || 57 < input[i])
	           bne $t7, $zero PErrorExit
	           slti $t7, $t6, 58
	           beq $t7, $zero, PErrorExit
	           addi $t6, $t6, -48        # char -48 to int
	           li $t7, 10
	           mult $t7, $s4    
	           mflo $t7          
	           add $s4, $t7, $t6        # num2 =(input[i]-48)+num2*10
	           addi $s0, $s0, 1   # input[++i]
	           j Num2Loop1 
     
	fractionalPart2: addi $s0, $s0, 1 #input[++i]
	        Num2Loop2: lb $t6, 0($s0)           # for(input[i] != '\n')             
                           beq $t6, $t0 PointPart
               	           slti $t7, $t6, 48         #  if(input[i]<48 || 57 < input[i])
	                   bne $t7, $zero PErrorExit
	                   slti $t7, $t6, 58
	                   beq $t7, $zero, PErrorExit
	                   addi $t6, $t6, -48        # char -48 to int
	                   li $t7, 10
	                   mult $t7, $s4
	                   mflo $t7
	                   add $s4, $t7, $t6  # num2 =(input[i]-48)+num2*10
	                   addi $s0, $s0, 1   # input[++i]
		           addi $s5, $s5, 1  # ++count2
	                   j Num2Loop2

	PointPart:                     # to calculate how many number after decimal point will be
		   beq $s7, $t4, Cross  # if(operator =='*')
		   beq $s2, $s5, Equ  #if(count1 == count2)
		   slt $t6, $s2, $s5
		   bne $t6, $zero, process1 # if (count1 < count2) 
		   j process2      # if (count2<count1)

         Equ: sw $s2, point  # point =count1 =count2
              j Pexit

	process1: sub $a0, $s5, $s2       # count2-count1
		  addi $sp, $sp, -4
 		  sw $ra, 0($sp)
		  jal Power               #10^count2-count1
                  lw $ra, 0($sp)
		  addi $sp, $sp, 4
		  mult $s1, $v0      # num1 *=power(10,count2-count1)
		  mflo $s1
		  sw $s5, point     # point = count2
                  j Pexit

	process2: sub $a0, $s2, $s5       # count1-count2 
		  addi $sp, $sp, -4
 		  sw $ra, 0($sp)
		  jal Power               # 10^count1-count2
                  lw $ra, 0($sp)
		  addi $sp, $sp, 4
		  mult $s4, $v0      # num2 *=power(10,count1-count2)
		  mflo $s4
		  sw $s2, point    # point =count1
                  j Pexit

	Cross: move $a0, $s1  # when operator is '*' program should delete needless zereoes after  decimal point
               move $a1, $s2
	       addi $sp, $sp, 4
	       sw $ra, 0($sp)
               jal DeleteZeroes  # calling deleteZeroes func. for num1
               move $s1, $v0
               move $s2, $v1
               lw $ra, 0($sp)
	       addi $sp, $sp, 4

               move $a0, $s4
               move $a1, $s5
	       addi $sp, $sp, 4
	       sw $ra, 0($sp)
               jal DeleteZeroes    # callng deleteZeroes func. for num2
               move $s4, $v0
               move $s5, $v1
               lw $ra, 0($sp)
	       addi $sp, $sp, 4
               
               add $t6, $s2, $s5 
	       sw $t6, point       # point =count1+count2
               j Pexit
		   

	Pexit:
		mult $s1, $s3       # num1 *=num1Sign
		mflo $s1           
		mult $s4, $s6      #num2 *=num2Sign
		mflo $s4
		sw $s1, num1     # saving num1
		sw $s4, num2	#saving num2
		sw $s7, operator  # saving operator
		jr $ra


        PErrorExit: li $v0, 4
	            la $a0, msgEr
	            syscall
           
		    li $v0, 10
		    syscall



PassSpace:        # passSpace ($a0 =&input[i])
	li $t6, 10 # newline character for end of string
	li $t7, 32 # space character

	Ploop: lb $t8, 0($a0)	
	       beq $t8, $t6, EndP   # if (input[i] =='\n') goto endP
	       bne $t8, $t7, EndP   # if (input[i] !=' ') goto endP
	       addi $a0, $a0, 1
	       j Ploop
	
	EndP:
	       move $v0, $a0
	       jr $ra

takeSign:    #takeSign($a0 =&input[i]) return $v0 =&input[i] $v1 = sign 1 or -1  
	li $t6, 45     # $t6 = 45 = '-'                                
	lb $t7, 0($a0)   # $t7 =input[i]
	addi $v1, $zero, 1  # number positive at default 
	bne $t6, $t7, EndTakeSign                        # if(input[i] =='-')
	addi $v1, $zero, -1 				#	numSign =-1;
	addi $a0, $a0, 1
	
	EndTakeSign:
		      move $v0, $a0
		      jr $ra


Power: addi $sp, $sp, -8    # a recursive function return 10^$a0
       sw $ra, 4($sp)
       sw $a0, 0($sp)
       bne $a0, $zero, L1
       addi $v0, $zero, 1
       addi $sp, $sp, 8
       jr $ra

       L1: addi $a0, $a0, -1
	   jal Power
	   lw $a0, 0($sp)
	   lw $ra, 4($sp)
	   addi $sp, $sp, 8
	   addi $t6, $zero, 10
	   mult $v0, $t6
	   mflo $v0
	   jr $ra

DeleteZeroes:       #DeleteZeroes($a0 =num, $a1= count )
             beq $a1, $zero, Dexit
             addi $t6, $zero, 10
             div $a0, $t6                          # while(num%10 ==0 && 0 <count)
             mfhi $t7                               #    num /=10;
             DLoop: bne $t7, $zero, Dexit            #   --count;
                    mflo $a0
		    addi $a1, $a1, -1      # count--
                    beq $a1, $zero, Dexit
		    div $a0, $t6
		    mfhi $t7
		    j DLoop
             
             Dexit: move $v0, $a0
                    move $v1, $a1
                    jr $ra


                    



	       	       







