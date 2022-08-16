package dto;

public enum FileDataType {
    INTEGER("-i"),
    STRING("-s");

    public String param;

    FileDataType(String param) {
        this.param = param;
    }

    public static FileDataType findByParam(String param){
        for(FileDataType type : values()){
            if(type.param.equals(param)){
                return type;
            }
        }
        return null;
    }
}
