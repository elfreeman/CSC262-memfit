package edu.smith.cs.csc262.memfit;

import java.util.Comparator;

public class ByOffset implements Comparator<Block> {
    @Override
    public int compare(Block lhs, Block rhs) {
        return Integer.compare(lhs.getOffset(), rhs.getOffset());
    }
}
