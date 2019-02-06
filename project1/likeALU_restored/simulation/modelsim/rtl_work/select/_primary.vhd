library verilog;
use verilog.vl_types.all;
entity \select\ is
    port(
        result          : out    vl_logic;
        cout            : out    vl_logic;
        inA             : in     vl_logic;
        inB             : in     vl_logic;
        cin             : in     vl_logic;
        s1              : in     vl_logic;
        s0              : in     vl_logic
    );
end \select\;
