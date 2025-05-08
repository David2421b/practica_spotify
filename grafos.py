class Graph:
  def __init__(self):
    self.adj_matrix: list[list[int]] = []
    self.nodes: list[int] = []
    self.size = 0
    self.nonweight_value = 0 #valor no representativo
    self.relation_value = 1

  def add_vertex(self, vertex_value):
    if(vertex_value in self.nodes):
      return

    #agrego a la lista de nodos
    self.nodes.append(vertex_value)
    self.size += 1

    #matriz de ady.
    for row in self.adj_matrix:
      row.append(self.nonweight_value)

    self.adj_matrix.append([self.nonweight_value] * self.size)

  def add_edge(self, vertex_1, vertex_2, directed = True, weight: int = None):
    #si no existen los vértices, los creo...
    if(vertex_1 not in self.nodes):
      self.add_vertex(vertex_1)

    if(vertex_2 not in self.nodes):
      self.add_vertex(vertex_2)

    pos_v1 = self.nodes.index(vertex_1)
    pos_v2 = self.nodes.index(vertex_2)

    relation_weight = self.relation_value if weight is None else weight

    if(not directed):
      self.adj_matrix[pos_v2][pos_v1] = relation_weight
    self.adj_matrix[pos_v1][pos_v2] = relation_weight


  def search(self, val):
    if val in self.nodes:
      print("El vertice se encuentra en la lista")

    else:
      print("el vertice no se encuenta en la lisata")


  def __repr__(self):
    repr_matrix = ""
    for row in self.adj_matrix:
      repr_matrix += str(row) + "\n"

    repr_matrix += f"\n{self.nodes}"

    return repr_matrix
  
  def dfs(current, lista_visitados: list):
    

g = Graph()
g.add_edge(1,3)
g.add_edge(2,10, directed = False)
g.add_edge(3,1, weight = 99, directed = False)
g.add_edge(4,6)
print(g)

g.search(5)