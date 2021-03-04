package os;

import constant.EnumConstants.EInterrupt;

public class Interrupt { // 이걸로 구조체를 만들어버림. 이걸 공유해서 쓸겅미. 이게 원래 레지스터인데 우리는 벌쳐형식으로 구조체로 만들어버린것.
	private EInterrupt eType;
	public EInterrupt geteType() { return eType; }
	public void seteType(EInterrupt eType) { this.eType = eType; } 
	
	private Object parameter;
	public Object getParameter() { return parameter; } 
	public void setParameter(Object parameter) { this.parameter = parameter; }

	public Interrupt() {
		this.eType = EInterrupt.eNone;
		this.parameter = null;
	}
}
