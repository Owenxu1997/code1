package com.tjj.bysjerp.sys.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owen on 2020/4/7 19:41
 */
public class TreeNodeBuilder {

    /**
     * 把没有层级关系的集合变成有层级关系的
     * @param treeNodes
     * @param topPid
     * @return
     */
    public static List<TreeNode> build(List<TreeNode> treeNodes,Integer topPid){
        List<TreeNode> nodes = new ArrayList<>();
        //每次循环找到一个父节点，再次循环找到该父节点下的子节点，并加入到父节点的list中
        for (TreeNode t1 : treeNodes) {
            if (t1.getPid()==topPid) {
                nodes.add(t1);
            }
            for (TreeNode t2 : treeNodes) {
                if (t1.getId()==t2.getPid()) {
                    t1.getChildren().add(t2);
                }
            }
        }
        return nodes;
    }
}
