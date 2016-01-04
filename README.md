# rangeAStar
AStar Path Finder in Range(eg. 10 step)

## Example
Run **MainView.java**

![rangeAstar](./images/rangeAstar.png)

### Cell Type And Cost（单元格颜色类型以及寻路代价）
TileCellType(int index, double cost, Color color, String desc)

* camp(2, 1, Color.WHITE, "大营")
* resurrection(15, Double.MAX_VALUE, new Color(192, 192, 192), "复活")
* road(6, 1, Color.YELLOW, "道路")
* forest(10, 2, new Color(0, 128, 0), "森林")
* shoal(44, 1, new Color(255, 204, 0), "浅滩")
* hills(22, Double.MAX_VALUE, new Color(255, 128, 128), "群山")
* rivers(33, Double.MAX_VALUE, new Color(0, 204, 255), "浅水")
* commonResource(18, 1, new Color(153, 51, 102), "普通")
* centerResource(1, 1, Color.BLACK, "中央")