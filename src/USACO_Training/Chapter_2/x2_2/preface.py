'''
ID: d3rrickl
LANG: PYTHON3
PROG: preface
'''
def m4(a):
   return a*"M"
def m3(a):
   x = int(a-5)
   if 0 <= a <= 3:
      return a*"C"
   elif a is 4:
      return "CD"
   elif a is 5:
      return "D"
   elif 6 <= a <= 8:
      return "D" + x*"C"
   elif a is 9:
      return "CM"
def m2(a):
   x = int(a-5)
   if 0 <= a <= 3:
      return a*"X"
   elif a is 4:
      return "XL"
   elif a is 5:
      return "L"
   elif 6 <= a <= 8:
      return "L" + x*"X"
   elif a is 9:
      return "XC"
def m1(a):
   x = int(a-5)
   if 0 <= a <= 3:
      return a*"I"
   elif a is 4:
      return "IV"
   elif a is 5:
      return "V"
   elif 6 <= a <= 8:
      return "V" + x*"I"
   elif a is 9:
      return "IX"   

def make_rom_num(N):
   ret = ''
   a = N % 10
   ret = m1(a) + ret
   N /= 10
   a = N % 10
   ret = m2(a) + ret
   N /= 10
   a = N % 10
   ret = m3(a) + ret
   N /= 10
   a = N % 10
   ret = m4(a) + ret
   return ret

def preface():
    with open("preface.in") as f:
      N = int(f.read())
    keys = ["I", "V", "X", "L", "C", "D", "M"]
    # rom_nums = {
#     "I" : None,
#     "V" : None, 
#     "X" : None, 
#     "L" : None, 
#     "C" : None, 
#     "D" : None, 
#     "M" : None 
#     }
    rom_nums = [0, 0, 0, 0, 0, 0, 0]   
    for i in range(1, N + 1):
       current = make_rom_num(i)
       for j in range(7):
          #print(rom_nums[j])
          rom_nums[j] += current.count(keys[j])
    with open("preface.out", "w") as f2:
      for k in range(7):
         if rom_nums[k] is not 0:
            f2.write(keys[k] + " " + str(rom_nums[k]) + "\n")

if __name__ == "__main__":
   preface()