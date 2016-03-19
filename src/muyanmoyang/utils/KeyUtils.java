package muyanmoyang.utils;

/**
 *	pair key�İ�װ��,�����洢�ʴ�ģ���еĴ�����
 * @author moyang
 *
 */
public class KeyUtils {

    private final int x;
    private final int y;

    public KeyUtils(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeyUtils)) return false;
        KeyUtils key = (KeyUtils) o;
        return x == key.x && y == key.y;
    }
//    @Override
//    public int hashCode() {
//        int result = x;
//        result = 31 * result + y;
//        return result;
//    }

}