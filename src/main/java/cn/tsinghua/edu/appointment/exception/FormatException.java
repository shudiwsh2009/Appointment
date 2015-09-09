package cn.tsinghua.edu.appointment.exception;

public class FormatException extends BasicException {

    private static final long serialVersionUID = -350130863605962591L;

    public FormatException(String _info) {
        super(_info);
    }

    @Override
    public String toString() {
        return String.format("FormatException[info='%s']", info);
    }

    @Override
    public void printStackTrace() {
        System.out.println(this.toString());
    }
}
