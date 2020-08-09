# info
证券财务信息建模

## 需求背景：
  随着对证券投资分析的深入，投资思路越见清晰，财务分析思路更加成熟！
  最近和几个投资朋友交流，朋友们均有自己琢磨的一套财务评估体系，
  这些财务评估体系各有优劣，但是在实际的资产评估的时候，朋友们采用手工录入的方式，过程及其不便；
  因此我规划开发一个证券财务信息建模的监控管理系统；
  
## 业务规划：
  实现功能：
    1、财务数据自动更新
    2、财报自动生成（三张表+结构解析+杜邦拆解）
    3、企业横向纵向比较一键生成
    4、财务模型自动生成
    
    
## todo
  财务模型自动生成
    v1.0固定生成一个财务模型：股票筛选条件、按照财务模型构建excel表格信息；

## end
  财务数据自动更新
    v1.0：  手动下载全量财务报表数据（性能较慢，insert修改为批量插入可以提高性能）