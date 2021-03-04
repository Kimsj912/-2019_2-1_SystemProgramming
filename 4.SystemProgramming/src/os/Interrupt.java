package os;

import constant.EnumConstants.EInterrupt;

public class Interrupt { // �̰ɷ� ����ü�� ��������. �̰� �����ؼ� ���Ϲ�. �̰� ���� ���������ε� �츮�� ������������ ����ü�� ����������.
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
