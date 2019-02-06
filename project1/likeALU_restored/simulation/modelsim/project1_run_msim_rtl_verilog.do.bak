transcript on
if {[file exists rtl_work]} {
	vdel -lib rtl_work -all
}
vlib rtl_work
vmap work rtl_work

vlog -vlog01compat -work work +incdir+C:/Users/ferhat/Desktop/bil\ organizasyon/161044080_prj1/likeALU_restored {C:/Users/ferhat/Desktop/bil organizasyon/161044080_prj1/likeALU_restored/likeALU.v}
vlog -vlog01compat -work work +incdir+C:/Users/ferhat/Desktop/bil\ organizasyon/161044080_prj1/likeALU_restored {C:/Users/ferhat/Desktop/bil organizasyon/161044080_prj1/likeALU_restored/select.v}
vlog -vlog01compat -work work +incdir+C:/Users/ferhat/Desktop/bil\ organizasyon/161044080_prj1/likeALU_restored {C:/Users/ferhat/Desktop/bil organizasyon/161044080_prj1/likeALU_restored/addbit.v}

vlog -vlog01compat -work work +incdir+C:/Users/ferhat/Desktop/bil\ organizasyon/161044080_prj1/likeALU_restored {C:/Users/ferhat/Desktop/bil organizasyon/161044080_prj1/likeALU_restored/likeALU_tb.v}

vsim -t 1ps -L altera_ver -L lpm_ver -L sgate_ver -L altera_mf_ver -L altera_lnsim_ver -L cycloneiii_ver -L rtl_work -L work -voptargs="+acc"  testALU

add wave *
view structure
view signals
run -all
