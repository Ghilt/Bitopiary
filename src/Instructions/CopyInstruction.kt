package Instructions

import OperatorType
import ExecutionTrack


class CopyInstruction(operator: Char, modifyInputChannel: Boolean, input: ArrayList<Char>, type: OperatorType ) : Instruction(operator, modifyInputChannel, input, type) {


    override fun execute(environment: ExecutionTrack) {
//            if (inputCaret){
//                environment.copyData(input)
//            } else {
//                environment.copyData(input)
//            }
    }


}