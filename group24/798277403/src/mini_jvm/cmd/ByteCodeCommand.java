package mini_jvm.cmd;

import mini_jvm.clz.ClassFile;
import mini_jvm.constant.ConstantInfo;
import mini_jvm.constant.ConstantPool;
import mini_jvm.engine.ExecutionResult;
import mini_jvm.engine.StackFrame;

import java.util.HashMap;
import java.util.Map;




public abstract class ByteCodeCommand {

	String opCode;
	ClassFile clzFile;
	private int offset;

	public static final String aconst_null = "01";
	public static final String new_object = "BB"; //创建一个对象
	public static final String lstore = "37";
	public static final String invokespecial = "B7"; //对一个对象进行初始化， 父类的初始化， 调用私有方法
	public static final String invokevirtual = "B6"; //调用对象的实例方法（多态）
	public static final String getfield = "B4";
	public static final String putfield = "B5"; //给一个对象的字段赋值
	public static final String getstatic = "B2";
	public static final String ldc = "12"; //从运行时常量池中提取数据压入栈中
	public static final String dup = "59"; //复制操作数栈栈顶的值，并压入到栈顶
	public static final String bipush = "10"; //将 byte 带符号扩展为一个 int 类型的值 value，然后将 value 压入到操作数栈中。
	public static final String aload_0 = "2A"; //从局部变量表中把index为0的reference的值加载到到操作数栈中
	public static final String aload_1 = "2B";
	public static final String aload_2 = "2C";
	public static final String iload = "15";
	public static final String iload_1 = "1B";
	public static final String iload_2 = "1C"; //从局部变量表中把index为2的int类型的值加载到到操作数栈中
	public static final String iload_3 = "1D";
	public static final String fload_3 = "25";

	public static final String voidreturn = "B1";
	public static final String ireturn = "AC"; //方法返回，从当前函数栈帧退出，  返回int
	public static final String freturn = "AE"; //方法返回，从当前函数栈帧退出，  返回float

	public static final String astore_1 = "4C"; //将栈顶的reference 类型数据保存到局部变量表中

	public static final String if_icmp_ge = "A2";
	public static final String if_icmple = "A4";
	public static final String goto_no_condition = "A7";
	public static final String iconst_0 = "03"; //将一个数值从操作数栈存储到局部变量表
	public static final String iconst_1 = "04";
	public static final String istore_1 = "3C";
	public static final String istore_2 = "3D";
	public static final String iadd = "60"; //加法指令
	public static final String iinc = "84"; //局部变量自增指令
	private static Map<String,String> codeMap = new HashMap<String,String>();

	static{
		codeMap.put("01", "aconst_null");

		codeMap.put("BB", "new");
		codeMap.put("37", "lstore");
		codeMap.put("B7", "invokespecial");
		codeMap.put("B6", "invokevirtual");
		codeMap.put("B4", "getfield");
		codeMap.put("B5", "putfield");
		codeMap.put("B2", "getstatic");

		codeMap.put("2A", "aload_0");
		codeMap.put("2B", "aload_1");
		codeMap.put("2C", "aload_2");

		codeMap.put("10", "bipush");
		codeMap.put("15", "iload");
		codeMap.put("1A", "iload_0");
		codeMap.put("1B", "iload_1");
		codeMap.put("1C", "iload_2");
		codeMap.put("1D", "iload_3");

		codeMap.put("25", "fload_3");

		codeMap.put("1E", "lload_0");

		codeMap.put("24", "fload_2");
		codeMap.put("4C", "astore_1");

		codeMap.put("A2", "if_icmp_ge");
		codeMap.put("A4", "if_icmple");

		codeMap.put("A7", "goto");

		codeMap.put("B1", "return");
		codeMap.put("AC", "ireturn");
		codeMap.put("AE", "freturn");

		codeMap.put("03", "iconst_0");
		codeMap.put("04", "iconst_1");

		codeMap.put("3C", "istore_1");
		codeMap.put("3D", "istore_2");

		codeMap.put("59", "dup");

		codeMap.put("60", "iadd");
		codeMap.put("84", "iinc");

		codeMap.put("12", "ldc");
	}


	protected ByteCodeCommand(ClassFile clzFile, String opCode){
		this.clzFile = clzFile;
		this.opCode = opCode;
	}

	protected ClassFile getClassFile() {
		return clzFile;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	protected ConstantInfo getConstantInfo(int index){
		return this.getClassFile().getConstantPool().getConstantInfo(index);
	}

	protected ConstantPool getConstantPool(){
		return this.getClassFile().getConstantPool();
	}



	public String getOpCode() {
		return opCode;
	}

	public abstract int getLength();

	public String getReadableCodeText(){
		String txt = codeMap.get(opCode);
		if(txt == null){
			return opCode;
		}
		return txt;
	}

	public abstract void execute(StackFrame frame,ExecutionResult result);
}