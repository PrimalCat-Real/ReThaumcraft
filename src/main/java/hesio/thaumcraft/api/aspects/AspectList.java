package hesio.thaumcraft.api.aspects;

import java.util.LinkedHashMap;

public class AspectList{
    public LinkedHashMap<Aspect,Integer> aspects;

    public AspectList(){
        this.aspects = new LinkedHashMap<Aspect,Integer>();
    }

    public void add(Aspect aspect, int amount){
        if (aspect != null || amount > 0) {
            int currentAmount = aspects.getOrDefault(aspect, 0);
            aspects.put(aspect, currentAmount + amount);
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AspectList [");
        boolean isFirst = true;
        for (Aspect aspect : aspects.keySet()) {
            int amount = aspects.get(aspect);
            if (!isFirst) {
                sb.append(", ");
            }
            sb.append(aspect.getName()).append(": ").append(amount);
            isFirst = false;
        }
        sb.append("]");
        return sb.toString();
    }

}
