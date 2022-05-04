import folium
import re
import sys

f = open(sys.argv[1])
line = f.readline()
ids = []
while line:
    ids.append(int(line))
    line = f.readline()
f.close()
# filename = "F:\\前端材料\\release\\taxi_log_2008_by_id\\1.txt"

m = folium.Map(location=[39.86, 116.55],
                    zoom_start=10,
                    control_scale=True)


colors = ['red', 'blue', 'green', 'purple', 'orange', 'darkred', 'lightred', 'beige', 'darkblue', 'darkgreen', 'cadetblue', 'darkpurple', 'white', 'pink', 'lightblue', 'lightgreen', 'gray', 'black', 'lightgray']

for i in range(len(ids)):
    filename = "D:\\轨迹数据\\release\\taxi_log_2008_by_id\\"+str(ids[i])+".txt"

    f = open(filename)  # 返回一个文件对象
    line = f.readline()  # 调用文件的 readline()方法
    res = []
    while line:
        #   print(line)           # 后面跟 ',' 将忽略换行符
        # print(line, end = '')　      # 在 Python 3 中使用
        line = line.replace(",", " ")
        # print(line)
        list = re.split(' ', line)
        # print(len(list))
        te = []
        te.append(float(list[4]))
        te.append(float(list[3]))
        res.append(te)

        line = f.readline()

    f.close()

    ls = folium.PolyLine(locations=res, weight=2,
                              color=colors[i])

    ls.add_to(m)

m.save('D:\\print2.html')

with open('D:\\print2.html') as fin:
    print(fin.read())


'''f = open("F:\\前端材料\\2.txt")  # 返回一个文件对象
line = f.readline()  # 调用文件的 readline()方法
res = []
while line:
    #   print(line)           # 后面跟 ',' 将忽略换行符
    # print(line, end = '')　      # 在 Python 3 中使用
    line = line.replace(",", " ")
    # print(line)
    list = re.split(' ', line)
    #   print(len(list))
    te = []
    te.append(float(list[4]))
    te.append(float(list[3]))
    res.append(te)

    line = f.readline()

f.close()

ls = folium.PolyLine(locations=res, weight=1,
                     color='red')
ls.add_to(m)'''
