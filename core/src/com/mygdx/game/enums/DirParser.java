package com.mygdx.game.enums;

public class DirParser {
    DirConst8 dir8;

//    DirConst2 to2(DirConst4 dir4){
//        return dir2;
//    }
//    DirConst2 to2(DirConst8 dir8){
//        return dir2;
//    }
//    @Contract(pure = true)
//    DirConst4 convert4(DirConst2 dir2){
//        return dir4;
//    }
//    DirConst4 convert4(DirConst8 dir8){
//        return dir4;
//    }
//    DirConst8 convert(DirConst2 dir2){
//        return dir8;
//    }
//    DirConst8 convert(DirConst4 dir4){
//        return dir8;
//    }
    public DirConst8 to8(DirConst4 dir4){
        switch (dir4) {
            case NULL:
                dir8 = DirConst8.NULL;
                break;
            case UP:
                dir8 = DirConst8.NORTH;
                break;
            case DOWN:
                dir8 = DirConst8.SOUTH;
                break;
            case LEFT:
                dir8 = DirConst8.WEST;
                break;
            case RIGHT:
                dir8 = DirConst8.EAST;
                break;
        }
        return dir8;
    }

}
